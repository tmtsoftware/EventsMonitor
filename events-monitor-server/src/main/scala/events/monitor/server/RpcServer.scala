package events.monitor.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.Future

class RpcServer(routes: Routes)(implicit system: ActorSystem) {
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  import materializer.executionContext

  def start(): Future[Http.ServerBinding] = Http().bindAndHandle(routes.route, interface = "0.0.0.0", port = 9000)
}
