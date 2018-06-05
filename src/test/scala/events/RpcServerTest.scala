package events

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import akka.stream.ActorMaterializer
import org.scalatest.FunSuite
import streaming.events.Routes

class RpcServerTest extends FunSuite with ScalatestRouteTest {

  implicit val mat: ActorMaterializer = ActorMaterializer()

  val routes: Route = new Routes().route

  test("Websocket path test") {
    val wsClient = WSProbe()
    WS("/streaming/start?from", wsClient.flow) ~> routes ~>
    check {
      // check response for WS Upgrade headers
      assert(isWebSocketUpgrade)
    }
  }
}
