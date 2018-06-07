package events.monitor.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.Future

class RpcServer(routes: Routes)(implicit system: ActorSystem) {
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  import materializer.executionContext

  def start(): Future[Http.ServerBinding] = {
    Http().bindAndHandleAsync(
      Route.asyncHandler(routes.route),
      interface = "0.0.0.0",
      port = 9443,
      connectionContext = ExampleHttpContexts.exampleServerContext
    )
    Http().bindAndHandleAsync(
      Route.asyncHandler(routes.route),
      interface = "0.0.0.0",
      port = 9000
    )
  }
}
