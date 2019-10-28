package contextual

import scala.annotation.alpha
import scala.collection.GenSeq

import scala.compiletime.{erasedValue, error, summonFrom}
import scala.deriving.{Mirror, productElement}

trait Monoid[T] extends Semigroup[T]
  def empty: T

object Monoid
  def apply[T](given Monoid[T]): Monoid[T] = summon[Monoid[T]]

  //syntax
  given [T: GenSeq]: {
    def (as: GenSeq[A]) foldMap[A, B] (f: A => B)(given Monoid[B]): B = ???
    def (ts: GenSeq[T]) combineAll[T] (given Monoid[T]): T = ??? // List(1, 2, 3).combineAll = 6
  }

  // instances
  given Monoid[String]
    val empty: String = ""
    @alpha("combine") def (x: String) |+| (y: String): String = x + y

  given Monoid[Int]
    val empty: Int = 0
    @alpha("combine") def (x: Int) |+| (y: Int): Int = x + y
  
  /*
    Create instances for numbers, sequences, options and endofunctions (A => A)
  */  

  /*
    Derives Monoid for simple plain products
    See https://github.com/travisbrown/dotty-experiments/blob/master/dotty-cats/kernel/src/main/scala/io/circe/cats/kernel/Monoid.scala
    For high level derivation see Shapeless 3 
    https://github.com/milessabin/shapeless/blob/shapeless-3/core/src/test/scala/shapeless/type-classes.scala#L48
  */
  inline def derived[T] (given mirror: Mirror.Of[T]): Monoid[T] =
    new Monoid[T] {
      val empty: T =
        inline mirror match
          case m: Mirror.ProductOf[T] => 
            m.fromProduct(summonEmpty[m.MirroredElemTypes].asInstanceOf[Product])

      @alpha("combine") def (x: T) |+| (y: T): T =
        inline mirror match
          case m: Mirror.ProductOf[T] =>
            m.fromProduct(summonCombine[m.MirroredElemTypes, T](x, y, 0).asInstanceOf[Product])
    }
  
  /*
    Mirror type class instances provide information at the type level about the components and labelling of the type
    Product types (case classes and objects, and enum cases) have mirrors which are subtypes of Mirror.Product. 
    Sum types (sealed class or traits with product children, and enums) have mirrors which are subtypes of Mirror.Sum.

    summonFrom patterns are tried in sequence. 
    the first case with a pattern x: T, such that an implicit value of type T can be summoned, is chosen.
    summonFrom applications must be reduced at compile time.
  */

  inline def summonMonoid[T]: Monoid[T] = summonFrom {
    case m: Monoid[T] => m
    // error method is used to produce user-defined compile errors during inline expansion
    case _ => error("given `Monoid` was not found ")
  }

  /*
    erasedValue function pretends to return a value of its type argument T:
      erased def erasedValue[T]: T = ???
    as erased function can never be called and not exists at run-time
    can only be used at compile-time during type checking
  */
  inline def summonEmpty[T <: Tuple]: Tuple = inline erasedValue[T] match
    case _: (t *: ts) => summonMonoid[t].empty *: summonEmpty[ts]
    case _: Unit => ()
  
  inline def summonCombine[T <: Tuple, A](x: A, y: A, idx: Int): Tuple = 
    inline erasedValue[T] match
      case _: (t *: ts) => 
        given Monoid[t] = summonMonoid
        ((productElement[t](x, idx)) |+| (productElement[t](y, idx)))
        *: summonCombine[ts, A](x, y, idx + 1)
      case _: Unit => ()

end Monoid
