package events.monitor.ui.r4s.components

import com.github.ahnfelt.react4s._

case class EventKeys(keys: P[Set[String]]) extends Component[NoEmit] {

  val selectedKeys: State[Set[String]] = State(Set.empty[String])

  private def updateState(get: Get, str: String): Unit = {
    if (!get(selectedKeys).contains(str)) {
      selectedKeys.modify(set => set + str)
    } else {
      selectedKeys.modify(list => list.filter(_ != str))
    }
  }

  private def renderKeys(keys: List[String]) = E.ul(Tags(for (key <- keys) yield E.li(Text(key))))

  override def render(get: Get): ElementOrComponent = {
    val eventKeys = get(keys).toList
    E.div(
      Tags(Text("items -->")),
      renderKeys(get(selectedKeys).toList),
      E.ul(
        Tags(
          eventKeys.map { key =>
            E.li(
              Tags(
                Text(key),
                E.input(A.`type`("checkbox"), A.value(key), A.onChange(_ => updateState(get, key)))
              )
            )
          }
        )
      )
    )
  }
}
