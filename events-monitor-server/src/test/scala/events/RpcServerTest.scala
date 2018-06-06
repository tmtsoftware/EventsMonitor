package events

import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import csw.messages.events.{Event, EventKey, EventName, SystemEvent}
import csw.messages.params.models.{Id, Prefix}
import events.monitor.server.Wiring
import org.scalatest.FunSuite

import scala.concurrent.duration.DurationDouble

class RpcServerTest extends FunSuite with ScalatestRouteTest {

  private val wiring = new Wiring
  val prefix         = Prefix("test.prefix")
  val eventName      = EventName("system")
  val eventKey       = EventKey(prefix, eventName)

  var counter = 0

  def makeEvent: Event = {
    counter += 1
    SystemEvent(prefix, eventName).copy(eventId = Id(counter.toString))
  }

  test("numbers") {
    val wsClient = WSProbe()

    WS("/stream/numbers?from=17", wsClient.flow) ~> wiring.routes.route ~> check {
      assert(isWebSocketUpgrade)
      wsClient.expectMessage("17")
      wsClient.expectMessage("18")
      wsClient.expectMessage("19")
      wsClient.expectMessage("20")
    }
  }

  test("subscribe") {
    val wsClient = WSProbe()

    wiring.publisher.publish(makeEvent, 1.second)

    WS(s"/subscribe/key/$eventKey", wsClient.flow) ~> wiring.routes.route ~> check {
      assert(isWebSocketUpgrade)
      while (true) {
        println(wsClient.expectMessage())
      }
    }
  }
}
