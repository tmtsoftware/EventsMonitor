package events

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Source}
import csw.messages.events.{Event, EventKey, EventName, SystemEvent}
import csw.messages.params.models.{Id, Prefix}
import csw.services.event.internal.redis.{RedisPublisher, RedisSubscriber}
import csw.services.event.scaladsl.{EventPublisher, EventSubscriber}
import events.TestFutureExt.RichFuture
import io.lettuce.core.{RedisClient, RedisURI}
import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationLong

class EventServiceTest extends FunSuite {
  implicit val system: ActorSystem             = ActorSystem("test-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val redisClient: RedisClient    = RedisClient.create()
  val redisURI: RedisURI          = RedisURI.create("localhost", 6379)
  val subscriber: EventSubscriber = new RedisSubscriber(redisURI, redisClient)
  val publisher: EventPublisher   = new RedisPublisher(redisURI, redisClient)

  val prefix    = Prefix("test.prefix")
  val eventName = EventName("system")
  val eventKey  = EventKey(prefix, eventName)

  var counter = 0

  def makeEvent: Event = {
    counter += 1
    SystemEvent(prefix, eventName).copy(eventId = Id(counter.toString))
  }

  test("publish and subscribe with same event key") {
    val resultF: Future[Done] =
      subscriber.subscribe(Set(eventKey)).runForeach(println)

    publisher.publish(makeEvent, 1.second)
    resultF.await(100.seconds)
  }

  test("streaming continuously from 0") {
    val source = Source.fromIterator(() => Iterator.from(0)).map(i => { Thread.sleep(1000); i.toString })
    val flow   = Flow[String].prepend(source)
    Source.empty.via(flow).runForeach(println).await(100.seconds)
  }
}
