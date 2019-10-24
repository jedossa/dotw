package contextual

import scala.collection.GenSeq

trait Monoid[T] extends Semigroup[T]
  def empty: T
object Monoid
  def apply[T](given Monoid[T]): Monoid[T] = summon[Monoid[T]]
  given [T: GenSeq]: {
    def (as: GenSeq[A]) foldMap[A, B] (f: A => B)(given Monoid[B]): B = ???
    def (ts: GenSeq[T]) combineAll[T] (given Monoid[T]): T = ??? // List(1, 2, 3).combineAll = 6
  }
/*
  Create instances for numbers, sequences, options and endofunctions (A => A)
*/