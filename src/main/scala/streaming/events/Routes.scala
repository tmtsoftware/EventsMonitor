package streaming.events

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Source}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

class Routes(implicit mat: Materializer) extends FailFastCirceSupport {

  val route: Route = cors() {
    get {
      path("streaming") {
        val messages = Source.fromIterator(() => Iterator.from(0)).map(i => TextMessage(i.toString))
        val flow     = Flow[Message].prepend(messages)
        handleWebSocketMessages(flow)
      }
    }
  }
}
