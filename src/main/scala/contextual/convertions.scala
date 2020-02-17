package contextual

import scala.language.implicitConversions
/*
  Implicit conversions are gone for good.

  Now implicit conversions are defined by given instances of the scala.Conversion class:

  abstract class Conversion[-T, +U] extends (T => U)
*/
object Conversions:
  opaque type Celcius = Double
  object Celcius:
    def apply(d: Double): Celcius = d
    given Conversion[Celcius, Fahrenheit] = c => (c * (9.0/5.0)) + 32
    extension on (c: Celcius) {
      def toF (using Conversion[Celcius, Fahrenheit]): Fahrenheit = summon[Conversion[?,?]](c)
    }

  opaque type Fahrenheit = Double
  object Fahrenheit:
    def apply(d: Double): Fahrenheit = d
    given Conversion[Fahrenheit, Celcius] = f => (f - 32) * (5.0/9.0)
    extension fahrenheitOps on (f: Fahrenheit):
      def toC (using to: Conversion[Fahrenheit, Celcius]): Celcius = to(f)

  val zeroCF: Fahrenheit = Celcius(0D)

  val zeroFC: Celcius =
    import Conversions.Fahrenheit.fahrenheitOps
    Fahrenheit(0D).toC
