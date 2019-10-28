package meta

val op = Logger.log[Int]("1 + 1")(1 + 1)

/*
  Expands to:

  val msg = "1 + 1" // by-value parameters are placed in a val
  println(s"${"  " * indent}start $msg")
  Logger.inline$indent_ += 1 // for private members the compiler generates a setter method
  val result = 1+1 // by-name parameters are inlined directly
  Logger.inline$indent_ -= 1
  println(s"${"  " * indent}$msg = $result")
  result
*/