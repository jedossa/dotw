package contextual

import scala.annotation.alpha

/*
  An @alpha annotation on a method definition defines an alternate name for the implementation of that method.
  The implementation name affects the code that is generated, 
  and is the name under which code from other languages can call the method
*/
trait Semigroup[T]:
  @alpha("combine") def (x: T) |+| (y: T): T // generic extension, extends type parameter T

object Semigroup:
  def apply[T](using Semigroup[T]): Semigroup[T] = summon[Semigroup[T]] // summoner

  //instances
  given [T](using Numeric[T]) as Semigroup[T]: // using [T: Numeric]
    @alpha("combine") def (x: T) |+| (y: T): T = summon[Numeric[T]].plus(x, y)
  given Semigroup[String] {
    @alpha("combine") def (x: String) |+| (y: String): String = s"$x $y"
  }
  given [T: Semigroup] as Semigroup[Option[T]] {
    @alpha("combine") def (x: Option[T]) |+| (y: Option[T]): Option[T] =
      x -> y match
        case (None, _) => y
        case (_, None) => x
        case (Some(a), Some(b)) => Some(a |+| b)
  }

object Example:
  import Semigroup.{given _}
  val int: Int = Semigroup[Int].|+|(2)(3) // calling "combine" does not work
  val double: Double = 2.5 |+| (3.0 |+| 1.1) // got syntax and instances via given
  val option: Option[String] = Some("Hello") |+| None |+| Some("there") |+| Option.empty[String]
  val sum = List(1, 2, 3).foldLeft(0)(_ |+| _)
  // how can we merge two maps by combining values with same key ?