package events

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import org.scalatest.FunSuite
import streaming.events.Routes

class RpcServerTest extends FunSuite with ScalatestRouteTest {

  val routes: Route = new Routes().route

  test("Websocket path test") {
    val wsClient = WSProbe()

    WS("/stream/numbers?from=17", wsClient.flow) ~> routes ~> check {
      // check response for WS Upgrade headers
      assert(isWebSocketUpgrade)
      wsClient.expectMessage("17")
      wsClient.expectMessage("18")
      wsClient.expectMessage("19")
      wsClient.expectMessage("20")
    }
  }
}
