package events

import akka.Done
import akka.stream.scaladsl.{Flow, Source}
import csw.messages.events.{Event, EventKey, EventName, SystemEvent}
import csw.messages.params.models.{Id, Prefix}
import events.TestFutureExt.RichFuture
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import streaming.events.Wiring

import scala.concurrent.Future
import scala.concurrent.duration.DurationLong

class EventServiceTest extends FunSuite with MockitoSugar {
  private val wiring = new Wiring
  import wiring._

  val prefix    = Prefix("test.prefix")
  val eventName = EventName("system")
  val eventKey  = EventKey(prefix, eventName)

  var counter = 0

  def makeEvent: Event = {
    counter += 1
    SystemEvent(prefix, eventName).copy(eventId = Id(counter.toString))
  }

  test("publish and subscribe with same event key") {
    val resultF: Future[Done] = subscriber.subscribe(Set(eventKey)).runForeach(println)

    publisher.publish(makeEvent, 1.second)
    resultF.await(100.seconds)
  }

  test("Flow.prepend") {
    val source =
      Source
        .fromIterator(() => Iterator.from(0))
        .throttle(1, 1.second)
        .map(_.toString)
    val flow = Flow[String].prepend(source)
    Source.empty.via(flow).runForeach(println).await(100.seconds)
  }
}
