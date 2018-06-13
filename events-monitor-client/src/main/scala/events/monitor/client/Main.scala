package events.monitor.client

import org.scalajs.dom.{EventSource, WebSocket}

object Main {
  def main(args: Array[String]): Unit = {
    println("hello!")
//    ws()
    sse()
  }

  def ws(): Unit = {
    val webSocket = new WebSocket("ws://localhost:9000/stream/numbers/ws?from=13")
    webSocket.onmessage = { messageEvent =>
      println(messageEvent.data)
    }
  }

  def sse(): Unit = {
    var counter     = 0
    val eventSource = new EventSource("/stream/numbers/sse?from=13")
    eventSource.onmessage = { messageEvent =>
      println(messageEvent.data)
      counter += 1
      if (counter == 10) {
        eventSource.close()
      }
    }
  }
}
