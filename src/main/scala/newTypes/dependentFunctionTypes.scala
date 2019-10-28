package newTypes

/*
  A dependent function type describes functions where the result type may depend on the function's parameter values.
*/
trait Sum[A <: Int, B <: Int]
  type R <: Int

object Sum
  /*
    Equivalent to dependent method
      def apply[A <: Int, B <: Int](given sum: Sum[A, B]): Aux[A, B, sum.R] = sum
    In scala 2 dependent methods could not be turned into functions
  */
  def apply[A <: Int, B <: Int]: (given sum: Sum[A, B]) => Aux[A, B, sum.R] = summon[Sum[A, B]]
  
  /*
    desugaring
      Function1[Sum, Aux[A, B, Sum#R]] {
        def apply[A <: Int, B <: Int](sum: Sum[A, B]): Aux[A, B, sum.R]
      }
  */

  type Aux[A <: Int, B <: Int, C <: Int] = Sum[A, B] { type R = C }
  
  given [B <: Int]: Sum[Zero, B] { type R = B }
  //or: implicit def zero[B <: Int]: Aux[Zero, B, B] = new Sum[Zero, B] { type R = B }
  
  val sumZero = Sum[0, 0] //what's the type ?

  /* 
    // define a type-level sum
    implicit def +[A <: Int, B <: Int]
    // a sample implementacion in Scala 2:
    // https://github.com/jedossa/fp-lab/blob/develop/src/main/scala/types/Numbers.scala
  */
end Sum
