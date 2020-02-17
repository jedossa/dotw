package enums

import scala.util.{Failure, Success, Try}

// Optional ADT
enum Maybe[+T]:
  case Just(t: T) // extends Maybe[T] // inferred extends clause 
  case Nil // extends Maybe[Nothing] // covariant type parameter minimized

/*
  desugaring
  sealed abstrac class Maybe[+T]
  final case class Just(t: T) extends Maybe[T]
  case object Nil extend Maybe[Nothing]
*/
  def isJust: Boolean = this match
    case Just(_) => true // as Just is a case class as well
    case Nil => false
  def isNone: Boolean = !isJust

  /*
    Just(1) =!= new Just(1), why ?
  */

object Maybe:
    def apply[T](t: T): Maybe[T] = if (t == null) Nil else Just(t)
    def [T](t: Try[T]).toMaybe: Maybe[T] = t match
      case Success(a) => Just(a)
      case Failure(_) => Nil