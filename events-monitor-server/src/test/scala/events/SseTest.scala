package events

import akka.NotUsed
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.scaladsl.{Keep, Sink, Source}
import csw.messages.events.{Event, EventKey, EventName, SystemEvent}
import csw.messages.params.models.{Id, Prefix}
import events.monitor.server.Wiring
import org.scalatest.FunSuite
import akka.http.scaladsl.unmarshalling.sse.EventStreamUnmarshalling._
import akka.stream.KillSwitches
import events.TestFutureExt.RichFuture

import scala.concurrent.duration.DurationDouble
import async.Async._

class SseTest extends FunSuite {

  private val wiring = new Wiring
  import wiring._

  test("numbers") {
    val request = HttpRequest(HttpMethods.GET, "http://localhost:9000/stream/numbers/sse?from=13")
    val dd = async {
      val httpResponse = await(Http().singleRequest(request))
      await(Unmarshal(httpResponse.entity).to[Source[ServerSentEvent, NotUsed]])
    }
    val sseStream = Source.fromFuture(dd).flatMapConcat(identity).viaMat(KillSwitches.single)(Keep.right)
    val switch    = sseStream.to(Sink.foreach(println)).run()
    Thread.sleep(5000)
    switch.shutdown()
    Thread.sleep(100000)
  }
}
