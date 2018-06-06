package events.monitor.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import csw.services.event.internal.pubsub.{EventPublisherUtil, EventSubscriberUtil}
import csw.services.event.scaladsl.{EventPublisher, EventSubscriber, RedisFactory}
import io.lettuce.core.RedisClient

import scala.concurrent.ExecutionContext

class Wiring {
  implicit val system: ActorSystem             = ActorSystem("App")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext            = system.dispatcher

  lazy val redisClient: RedisClient = RedisClient.create()
  lazy val eventPublisherUtil       = new EventPublisherUtil()
  lazy val eventSubscriberUtil      = new EventSubscriberUtil()
  lazy val redisFactory             = new RedisFactory(redisClient, null, eventPublisherUtil, eventSubscriberUtil)

  lazy val REDIS_HOST                  = "localhost"
  lazy val REDIS_PORT                  = 6379
  lazy val subscriber: EventSubscriber = redisFactory.subscriber(REDIS_HOST, REDIS_PORT)
  lazy val publisher: EventPublisher   = redisFactory.publisher(REDIS_HOST, REDIS_PORT)

  lazy val eventsMonitorServer = new EventsMonitorServer(subscriber)
  lazy val routes              = new Routes(eventsMonitorServer)
  lazy val rpcServer           = new RpcServer(routes)
}
