package contextual

trait Functor[F[?]]
  def (fa: F[A]) map[A, B] (f: A => B): F[B]
object Functor
  def apply[F[?]](given F: Functor[F]): Functor[F] = F
  given [A, F[?]]: {
    def (fa: F[A]) as[A, B] (b: B)(given Functor[F]): F[B] = fa.map(_ => b)
    def (fa: F[A]) void[A] (given Functor[F]): F[Unit] = fa.as(())
  }
/*
  Create instances for numbers, sequences and options
*/