package functions

import org.junit.Test
import org.junit.Assert.assertEquals

class FunctionsTest 
  def sum(x: Int, y: Int): Int = x + y

  @Test def etaExpansion: Unit =
    /*
      In Scala 2 sum is a method: (x: Int, y: Int)Int
      we can make sum a function by writing
      `sum _` or `sum(_,_)`
      In dotty the syntax sum _ is no longer needed
    */
    val suma: (Int, Int) => Int = sum // automatic eta expasion
    assertEquals(sum(1, 2), suma(2, 1))

    val curriedSum: Int => Int => Int = sum.curried
    assertEquals(sum(1, 2), curriedSum(2)(1))

  @Test def tupledFunction: Unit =
    /*
      compiler will synthesize an instance of TupledFunction[F, G]
      a type class that provides a way to abstract directly over a 
      function of any arity converting it to an equivalent function 
      that receives all arguments in a single tuple
    */  
    val tupledSum: ((Int, Int)) => Int = sum.tupled
    assertEquals(sum(2, 1), tupledSum(1 -> 2))

    /* 
      Creates an untupled version of a function: instead of single tuple argument,
      it accepts a N arguments.

      F the function type
      T the tuple type with the same types as the function arguments of F
      R the return type of F
    */
    def (f: T => R) untupled[F, T <: Tuple, R](given tf: TupledFunction[F, T => R]): F = 
      tf.untupled(f)

    val untupledSum: (Int, Int) => Int = tupledSum.untupled
    assertEquals(tupledSum(2 -> 1), untupledSum(1, 2))

  @Test def untupling: Unit =
    val triples: List[(Int, Int, Int)] = List((1, 1, 1), (2, 2, 2), (3, 3, 3))
    assertEquals(
      triples.map( _ + _ - _ ) ++
      triples.map( (x, y, z) => x + y - z ) ++
      // above map functions are converted to next pattern
      triples.map{ case (x, y, z) => x + y - z } // only this expresion works in Scala 2
      sum,
      18)
