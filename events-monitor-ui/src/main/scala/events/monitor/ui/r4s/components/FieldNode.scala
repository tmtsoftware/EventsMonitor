package events.monitor.ui.r4s.components

case class FieldNode(
    label: String,
    children: List[FieldNode]
)
