package events.monitor.ui.r4s

import com.github.ahnfelt.react4s._
import events.monitor.ui.r4s.components.StreamingContainer
import org.scalajs.dom.EventSource

case class MainComponent() extends Component[NoEmit] {
  val streamData: State[String]                         = State("")
  val DELIMETER_TO_SHOW_SEPARATE_STREAM_ELEMENT: String = ("*" * 100)
  val HOST_IP                                           = "10.131.21.67"
  /*
    Start `event-monitor-server` on local box, give host address as shown below.
    localhost or broadcast address is not acceptable due to CORS error in the browser
   */
  def makeSseRequest(): Unit = {
    val eventSource = new EventSource(s"http://$HOST_IP:9000/stream/json/sse")
    eventSource.onmessage = { messageEvent =>
      streamData.modify(_.concat(s"${messageEvent.data.toString}\n $DELIMETER_TO_SHOW_SEPARATE_STREAM_ELEMENT\n"))
    }
  }

  makeSseRequest()

  override def render(get: Get): ElementOrComponent = {
    E.div(
      Component(StreamingContainer, get(streamData))
    )
  }
}
