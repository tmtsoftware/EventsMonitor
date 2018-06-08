package events.monitor.ui.r4s.components

case class ParamSet(paramSet: List[Param])
case class Param(keyName: String, keyType: String, units: String, values: List[ParamSet] = List.empty[ParamSet])
