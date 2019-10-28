package enums
/*
   GADTs: https://www.youtube.com/watch?v=VV9lPg3fNl8&list=PL_5uJkfWNxdl-_ZLKDztCXceKD0l9Kz6n&index=10
*/
import scala.compiletime.S // type S[X <: Int] <: Int, sounds familiar ?
// More at https://dotty.epfl.ch/docs/reference/metaprogramming/inline.html#the-scalacompiletime-package

enum LList[+T, L <: Int]
  case LNil extends LList[Nothing, 0]
  case LCons[+T, L <: Int](head: T, tail: LList[T, L]) extends LList[T, S[L]]

import LList._
val cons: LList[Int, 2] = LCons(1,LCons(2,LNil))
