package events.monitor.api

import akka.stream.scaladsl.Source

trait EventsMonitor {
  def subscribe(key: String): Source[String, Any]
}
