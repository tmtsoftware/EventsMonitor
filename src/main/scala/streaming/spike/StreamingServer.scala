package streaming.spike

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult._
import akka.stream.{ActorMaterializer, OverflowStrategy}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import covenant.ws.AkkaWsRoute
import covenant.ws.api.WsApiConfigurationWithDefaults
import mycelium.server.WebsocketServerConfig

object StreamingServer {
  import chameleon.ext.circe._
  import io.circe.generic.auto._
  import sloth._

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("server")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    import monix.execution.Scheduler.Implicits.global

    val api = new WsApiConfigurationWithDefaults[Int, String, List[Int]] {
      override def dsl: StreamingDsl.type = StreamingDsl
      override def initialState: List[Int] = Nil
      override def isStateValid(state: List[Int]): Boolean = state.length < 10
      override def serverFailure(error: ServerFailure): String = error.toString
    }
    val config = WebsocketServerConfig(bufferSize = 5,
                                       overflowStrategy = OverflowStrategy.fail)
    val wsRouter = Router[String, StreamingDsl.ApiFunction]
      .route[Streaming[StreamingDsl.ApiFunction]](StreamingImpl)

    val route = cors() {
      pathPrefix("something-later") {
        complete("test-done")
      } ~
        pathPrefix("ws") {
          AkkaWsRoute.fromApiRouter(wsRouter, config, api)
        }
    }

    Http().bindAndHandle(route, interface = "0.0.0.0", port = 9090)
  }
}
