package events.monitor.server

object RpcApp {
  def main(args: Array[String]): Unit = {
    val wiring = new Wiring
    import wiring._
    rpcServer.start()
  }
}
