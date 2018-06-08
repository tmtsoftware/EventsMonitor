package events.monitor.ui.r4s.components

import com.github.ahnfelt.react4s._

case class StreamingContainer(data: P[String]) extends Component[NoEmit] {
  override def render(get: Get): Node = E.pre(Text(get(data)))
}
