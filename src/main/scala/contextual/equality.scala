package contextual

import scala.language.strictEquality

/*
  Universal equality can lead to runtime errors as undermines typasafety
  class A
  class B
  A() == B() // is always false but typechecks 
  
  With the above import you'll got (at compiletime):
    Values of types A and B cannot be compared with == or !=
*/
class A(i: Int) derives Eql // generates a Eql instance in A's companion object
/*
object A
  given [T](given Eql[Int, T]): Eql[A[Int], A[T]] = Eql.derived
*/

class B(s: String)
object B
  given [T]: Eql[B, T] = Eql.derived 
/* 
  Eql is a binary typeclass to indicate two types can be compare to one another
  By default, all numbers are comparable, because of:
  implicit def eqlNumber: Eql[Number, Number] = derived
  3 == 5.1

  By default, all Sequences are comparable, because of:
  implicit def eqlSeq[T, U](implicit eq: Eql[T, U]): Eql[GenSeq[T], GenSeq[U]] = derived
  List(1, 2) == Vector(1, 2)
*/
object Compare
  export B.{given Eql}

  given Eql[Int, String] = Eql.derived

  val intString = 1 != "1"

  val AA = A(1) == A(1)

  val BB = B("1") == B("1")

  val BA = B("1") != A(1)

/*
  // the following does not typecheck, how can we fix it ?
  val stringInt = "1" == 1

  val AB = A(1) != B("1")
*/
end Compare