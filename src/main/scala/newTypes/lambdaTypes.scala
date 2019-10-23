package newTypes

/*
  A type lambda lets one express a higher-kinded type directly, without a type definition
*/
trait MError[F[?], Throwable] // ? is the new wildcard for types

type ID[A] = A //expands to: `type ID = [A] =>> A`
type T2 = [X] =>> (X, X) // `type T2[X] = (X, X)`
type Eff[F[?]] = MError[F, Throwable] // make it a plain type defition

trait Effect
  def unsafeRunR[F[?]: Eff]: Unit
  def unsafeRunL[F[?]: [L[?]] =>> MError[L, Exception]]: Unit // using an anonymous type parameter
  // coming soon: MError[_[?], Exception]