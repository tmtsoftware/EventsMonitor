package streaming

import scala.language.higherKinds

trait Streaming[Result[_]] {
  def from(a: Int): Result[String]
}
