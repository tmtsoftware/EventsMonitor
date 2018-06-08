package events.monitor.ui.r4s.components

import com.github.ahnfelt.react4s._

case class FieldPathComponent(node: P[List[ParamSet]]) extends Component[NoEmit] {

  def renderTodoList(nodes: Param) =
    E.ul(
      Tags(
        E.li(Text(nodes.keyName)),
        E.li(Text(nodes.keyType)),
        E.li(Text(nodes.units)),
        Component(FieldPathComponent, nodes.values)
      )
    )

  override def render(get: Get) = {
    val paramSets = get(node)
    E.div(
      Tags(
        get(node).flatMap { parentSet =>
          parentSet.paramSet.map(param => renderTodoList(param))
        }
      )
    )
  }
}
