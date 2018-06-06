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
    val eventSource = new EventSource("http://localhost:9000/stream/numbers/sse?from=13")
    eventSource.onmessage = { messageEvent =>
      println(messageEvent.data)
    }
  }
}
