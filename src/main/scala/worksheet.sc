println("Hello, worksheets!")

val xyz = 123
456 + xyz

given Int = 10 
given x as String = "foo"
given f(using x: Int) as Option[Int] = Some(x * x)
given [T](using opt: Option[T]) as List[T] = opt.toList

println(summon[List[Int]])

def foo(using x: Int): Option[Int] = Some(x * x)
def bar = Some(10)
val res = for
  given x: Int <- bar
  res <- foo
yield res

println(res)

import scala.deriving.Mirror

case class Repr[T](first: String, second: Int, third: T)

val mirror = summon[Mirror{ type MirroredType = Repr[Option[Int]] }]
