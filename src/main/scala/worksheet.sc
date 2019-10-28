println("Hello, worksheets!")

val xyz = 123
456 + xyz

given Int = 10 
given x: String = "foo"
given f(given x: Int): Option[Int] = Some(x * x)
given [T](given opt: Option[T]): List[T] = opt.toList

println(summon[List[Int]])

def foo(given x: Int): Option[Int] = Some(x * x)
def bar = Some(10)
val res = for
  given x: Int <- bar
  res <- foo
yield res

println(res)

import scala.deriving.Mirror

case class Repr[T](first: String, second: Int, third: T)

val mirror = summon[Mirror{ type MirroredType = Repr[Option[Int]] }]
