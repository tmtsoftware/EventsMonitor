package events.monitor.ui.r4s

import com.github.ahnfelt.react4s._
import events.monitor.ui.r4s.components.{EventKeys, FieldNode, FieldRootComponent}

case class MainComponent() extends Component[NoEmit] {
  override def render(get: Get): ElementOrComponent = {
    E.div(
      Component(EventKeys, Set("Bharat", "Poorva")),
      Component(
        FieldRootComponent,
        List(
          FieldNode(
            "Pantherinae",
            List(
              FieldNode("Panthera",
                        List(
                          FieldNode("Tiger", List()),
                          FieldNode("Lion", List()),
                          FieldNode("Jaguar", List()),
                          FieldNode("Leopard", List())
                        ))
            )
          ),
          FieldNode(
            "Felinae",
            List(
              FieldNode("Lynx",
                        List(
                          FieldNode("Canadian lynx", List()),
                          FieldNode("Eurasian lynx", List()),
                          FieldNode("Iberian lynx", List()),
                          FieldNode("Bobcat", List())
                        )),
              FieldNode("Puma",
                        List(
                          FieldNode("Cougar", List()),
                          FieldNode("Eyra", List())
                        )),
              FieldNode("Acinonyx",
                        List(
                          FieldNode("Cheetah", List())
                        ))
            )
          )
        )
      )
    )
  }
}
