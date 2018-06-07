package events.monitor.ui.r4s

import com.github.ahnfelt.react4s._
import events.monitor.ui.r4s.components.ParentSet
import events.monitor.ui.r4s.facade.NpmReactBridge
import io.circe.generic.auto._
import io.circe.parser._

object Main {
  def main(arguments: Array[String]): Unit = {
    val component = Component(MainComponent)
    val json =
      """{
        |  "type": "ObserveEvent",
        |  "eventId": "7a4cd6ab-6077-476d-a035-6f83be1de42c",
        |  "source": {
        |    "subsystem": "wfos",
        |    "prefix": "wfos.blue.filter"
        |  },
        |  "eventName" : {
        |    "name": "currentState"
        |  },
        |  "eventTime": "2017-08-09T06:40:00.898Z",
        |  "paramSet": [
        |    {
        |      "keyName": "myStruct",
        |      "keyType": "StructKey",
        |      "values": [
        |        {
        |          "paramSet": [
        |            {
        |              "keyName": "ra",
        |              "keyType": "StringKey",
        |              "units": "NoUnits"
        |            },
        |            {
        |              "keyName": "dec",
        |              "keyType": "StringKey",
        |              "units": "NoUnits",
        |              "values": []
        |            },
        |            {
        |              "keyName": "epoch",
        |              "keyType": "DoubleKey",
        |              "units": "NoUnits",
        |              "values": []
        |            }
        |          ]
        |        }
        |      ],
        |      "units": "NoUnits"
        |    }
        |  ]
        |}""".stripMargin

    println(json)
    val domainModel = decode[ParentSet](json)
    println(domainModel)
    NpmReactBridge.renderToDomById(component, "main")
  }
}
