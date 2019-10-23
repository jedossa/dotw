package contextual

trait Functor[F[?]]
  def (fa: F[A]) map[A, B] (f: A => B): F[B]
object Functor
  def apply[F[?]](given F: Functor[F]): Functor[F] = F
