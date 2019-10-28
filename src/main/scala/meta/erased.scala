package meta

/*
  erased terms are expresions that only exists at compile-time
  at run-time they won't exist, erased params can't be used for computations

  erased val ev: Ev = ???
  def f[T](erased ev: Ev): T = ???
  erased def g[T]: erased Ev => T = ???

  More at https://dotty.epfl.ch/docs/reference/metaprogramming/erased-terms.html
*/

/*
  Define and state machine as in 
  https://github.com/debasishg/frdomain/blob/master/src/main/scala/frdomain/ch4/patterns/Phantom.scala
  using erased terms instead of phatom types
*/
object State