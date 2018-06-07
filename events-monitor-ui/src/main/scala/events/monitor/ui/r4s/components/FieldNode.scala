package events.monitor.ui.r4s.components

case class FieldNode(
    label: String,
    children: List[FieldNode]
)

case class ParentSet(paramSet: List[ParamSet])
case class ParamSet(keyName: String, keyType: String, units: String, values: Option[List[ParentSet]])
