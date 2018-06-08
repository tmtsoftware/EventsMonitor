package events.monitor.ui.r4s

import com.github.ahnfelt.react4s._
import events.monitor.ui.r4s.components.{FieldPathComponent, ParamSet}
import events.monitor.ui.r4s.facade.NpmReactBridge
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.auto._
import io.circe.parser._

object Main {
  def main(arguments: Array[String]): Unit = {
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
        |              "units": "NoUnits"
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
        |    },
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
        |              "units": "NoUnits"
        |            },
        |            {
        |              "keyName": "epoch",
        |              "keyType": "DoubleKey",
        |              "units": "NoUnits",
        |              "values": [
        |              {
        |          "paramSet": [
        |            {
        |              "keyName": "ra",
        |              "keyType": "StringKey",
        |              "units": "NoUnits"
        |            },
        |            {
        |              "keyName": "dec",
        |              "keyType": "StringKey",
        |              "units": "NoUnits"
        |            },
        |            {
        |              "keyName": "epoch",
        |              "keyType": "DoubleKey",
        |              "units": "NoUnits",
        |              "values": []
        |            }
        |          ]
        |        }
        |
        |              ]
        |            }
        |          ]
        |        }
        |      ],
        |      "units": "NoUnits"
        |    }
        |  ]
        |}""".stripMargin

    println(json)
    implicit val customConfig: Configuration = Configuration.default.withDefaults
    val domainModel                          = decode[ParamSet](json)
    val component: ElementOrComponent = domainModel match {
      case Left(value) =>
        println(s"Error in parsing json $value")
        Component(FieldPathComponent, List())
      case Right(value) =>
        println(s"Parsing success $value")
        Component(FieldPathComponent, List(value))
    }

    val mainComp = Component(MainComponent)
    NpmReactBridge.renderToDomById(mainComp, "main")
  }
}
