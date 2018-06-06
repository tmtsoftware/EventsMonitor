package streaming.spike

import scala.language.higherKinds

trait Streaming[Result[_]] {
  def from(a: Int): Result[String]
}
