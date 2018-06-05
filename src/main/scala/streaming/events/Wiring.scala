package streaming.events

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

class Wiring {
  implicit val system: ActorSystem             = ActorSystem("App")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val routes    = new Routes()
  val rpcServer = new RpcServer(routes)
}
