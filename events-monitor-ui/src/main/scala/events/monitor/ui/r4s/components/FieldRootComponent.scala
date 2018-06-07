package events.monitor.ui.r4s.components

import com.github.ahnfelt.react4s._

case class FieldRootComponent(nodes: P[List[FieldNode]]) extends Component[NoEmit] {
  override def render(get: Get) = E.div(
    E.h3(Text("Event Topic Fields")),
    E.ul(
      Tags(
        for ((item, index) <- get(nodes).zipWithIndex)
          yield
            E.li(
              Component(FieldNodeComponent, item)
            )
      )
    )
  )
}
