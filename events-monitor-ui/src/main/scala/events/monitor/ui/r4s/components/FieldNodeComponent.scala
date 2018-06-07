package events.monitor.ui.r4s.components

import com.github.ahnfelt.react4s._

case class FieldNodeComponent(node: P[FieldNode]) extends Component[NoEmit] {

  override def render(get: Get) =
    E.div(
      E.p(Text(get(node).label)),
      renderTodoList(get(node).children),
    )

  def renderTodoList(nodes: List[FieldNode]) =
    E.ul(
      Tags(
        for ((item, index) <- nodes.zipWithIndex)
          yield
            E.li(
              Component(FieldNodeComponent, item)
            )
      )
    )
}
