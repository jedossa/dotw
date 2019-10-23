package contextual

trait Monoid[T] extends Semigroup[T]
  def empty: T
object Monoid
  def apply[T](given Monoid[T]): Monoid[T] = summon[Monoid[T]]
