package events.monitor.server

import java.io.File

import akka.NotUsed
import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Source}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import akka.http.scaladsl.marshalling.sse.EventStreamMarshalling._
import akka.http.scaladsl.model.sse.ServerSentEvent

import scala.concurrent.duration.DurationDouble

class Routes(eventsMonitorServer: EventsMonitorServer)(implicit mat: Materializer) extends FailFastCirceSupport {
  val route: Route = cors() {
    get {
      pathSingleSlash {
        getFromResource("pages/index.html")
      } ~
      path("events-monitor-client-fastopt.js") {
        getFromResource("events-monitor-client-fastopt.js")
      } ~
      path("subscribe" / "key" / Segment) { keyName =>
        val messages = eventsMonitorServer.subscribe(keyName).map(TextMessage.Strict)
        val flow     = Flow[Message].prepend(messages)
        handleWebSocketMessages(flow)
      } ~
      pathPrefix("stream") {
        path("numbers") {
          parameter("from" ? 0) { startFrom =>
            val stream = numbers(startFrom)
            path("ws") {
              val flow = Flow[Message].prepend(stream.map(i => TextMessage(i.toString)))
              handleWebSocketMessages(flow)
            } ~
            path("sse") {
              complete {
                stream.map(x => ServerSentEvent(x.toString))
              }
            }
          }
        }
        path("json" / "sse") {
          implicit val jsonStreamingSupport: JsonEntityStreamingSupport = EntityStreamingSupport.json()
          complete {
            json().map(ServerSentEvent(_))
          }
        }
      }
    }
  }

  def numbers(startFrom: Int): Source[Int, NotUsed] = {
    Source
      .fromIterator(() => Iterator.from(startFrom))
      .throttle(1, 1.second)
  }

  def json(): Source[String, NotUsed] = {
    Source
      .repeat[String](scala.io.Source.fromResource("events.json").getLines().mkString("\n"))
      .throttle(1, 1.second)
  }
}
