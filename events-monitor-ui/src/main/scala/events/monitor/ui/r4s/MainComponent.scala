package events.monitor.ui.r4s

import com.github.ahnfelt.react4s._
import events.monitor.ui.r4s.components.EventKeys

case class MainComponent() extends Component[NoEmit] {
  override def render(get: Get): ElementOrComponent = {
    E.div(Component(EventKeys, Set("Bharat", "Poorva")))
  }
}
