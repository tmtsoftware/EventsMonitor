package events.monitor.server

import akka.actor.{typed, ActorSystem}
import akka.actor.typed.scaladsl.adapter._
import akka.stream.ActorMaterializer
import csw.services.event.internal.redis.RedisEventServiceFactory
import csw.services.event.scaladsl.{EventPublisher, EventService, EventSubscriber}

import scala.concurrent.duration.DurationDouble
import scala.concurrent.{Await, ExecutionContext}

class Wiring {
  implicit val system: ActorSystem             = ActorSystem("App")
  implicit val system1: typed.ActorSystem[_]   = ActorSystem("App").toTyped
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext            = system.dispatcher

  lazy val eventService: EventService  = RedisEventServiceFactory.make("localhost", 6379)
  lazy val subscriber: EventSubscriber = Await.result(eventService.defaultSubscriber, 2.seconds)
  lazy val publisher: EventPublisher   = Await.result(eventService.defaultPublisher, 2.seconds)
  lazy val eventsMonitorServer         = new EventsMonitorServer(subscriber)

  lazy val routes    = new Routes(eventsMonitorServer)
  lazy val rpcServer = new RpcServer(routes)
}
