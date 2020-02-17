package meta

/* 
  `inline` is a modifier which forces the compiler to recompile 
  the code inline at the point of use.

  This exists for 2 reasons:
  1. Advanced users can, with care, optimise code to remove indirection costs.
  2. The inline functionality enables Scala 3's macro system.

  More at http://slides.com/nicolasstucki/scala-days-2019
*/

object Logger:
  // constant value with a constant expression, here `inline` is equivalent to `final`
  inline val name: String = "SimpleLogger"

  private var indent: Int = 0 // change this var name and see how many files are compiled

  inline def log[T](msg: String)(op: => T): T =
      println(s"${"  " * indent}start $msg")
      indent += 1
      val result = op
      indent -= 1
      println(s"${"  " * indent}$msg = $result")
      result

end Logger

object Pow:
  /*
    Inline methods can be recursive.
    Power will be implemented by straight inline code without any loop or recursion.
    
    power(expr, 10)
    
    expands to, if n is a constant statically known value
      val x = expr
      val y1 = x * x   // ^2
      val y2 = y1 * y1 // ^4
      val y3 = y2 * x  // ^5
      y3 * y3          // ^10

    See https://dotty.epfl.ch/docs/reference/metaprogramming/inline.html   
  */
  inline def power(x: Long, n: Int): Long =
    if (n == 0) 
      1L
    else if (n % 2 == 1)
      x * power(x, n - 1)
    else
      val y: Long = x * x 
      power(y, n / 2)
  /*
    Conditionals and match expressions can be inlined if there's 
    enough static information to unambiguously take a branch

    inline if (true) 1 else 0 // rewrites to 1
    inline false match
      case true => 1
      case false => 0
    // rewirtes to 0  

    Parameters can be inlined as well

    inline def power(x: Long, inline n: Int)

    // hint: use any combination of these at power to fix the below
  */

  /*  
  power(2, 2) // = 4
  val n = 2
  power(2, n) // oops
  */

  /*  
  def pow(x: Long, n: Int): Long =
    power(x, n) // fix me
  */
