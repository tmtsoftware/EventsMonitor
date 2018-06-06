package events.monitor.server

import akka.stream.scaladsl.Source
import csw.messages.events.{EventKey, EventName}
import csw.messages.params.models.Prefix
import csw.services.event.scaladsl.EventSubscriber

class EventsMonitorServer(eventSubscriber: EventSubscriber) {

  import csw.messages.params.formats.JsonSupport

  def subscribe(key: String): Source[String, Any] = {
    eventSubscriber.subscribe(Set(from(key))).map(event => JsonSupport.writeEvent(event).toString())
  }

  private def from(eventKeyStr: String): EventKey = {
    require(eventKeyStr != null)
    val strings = eventKeyStr.splitAt(eventKeyStr.lastIndexOf("."))
    new EventKey(Prefix(strings._1), EventName(strings._2.tail))
  }
}
