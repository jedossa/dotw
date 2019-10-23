@main def welcome(name: String, surname: String) = 
  val msg = s"Hello $name $surname, welcome to Dotty workshop!"
  println(msg)
