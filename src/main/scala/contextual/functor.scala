package contextual

trait Functor[F[?]]:
  def [A, B] (fa: F[A]) map (f: A => B): F[B]
object Functor:
  def apply[F[?]](using F: Functor[F]): Functor[F] = F
  extension on [F[?]: Functor, A, B] (fa: F[A]) { //or: (using Functor[F])
    def as(b: B): F[B] = fa.map(_ => b)
    def void: F[Unit] = fa.as(())
  }
/*
  Create instances for numbers, sequences and options
*/
