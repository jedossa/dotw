package contextual

/*
  given replaces implicit: show intent instead of mechanism
*/
object Printer:
  opaque type Printable = [T] =>> String
  object Printable:
    def apply[T]: T => Printable[T] = _.toString
    extension printOps on (p: Printable[T]) { // public extension methods for opaque type
      def prettyPrint[T]: Unit = println(p)
    }

trait Executor[T]:
  export Printer.Printable // export creates an alias for an object selected member
  // final type PrintableType = Printer.Printable
  def trace: Printable[T] ?=> Unit = summon[Printable[T]].prettyPrint // def summon[T](using x: T): x.type = x
  // trace is a context function type (?=>)

trait Logger:
  def log: String => Unit = println(_)

class Task[T](value: T):
  export Printer.{Printable => Pretty}
  def unsafeRunSync[F: Executor](name: String)(using logger: Logger)(using Pretty[F]): Unit = // naming this is difficult
    logger.log(s"$name $value is running!")
    summon[Executor[F]].trace // how to pass a Printable explicitly ?
  /*
    Context bounds expands to
    def unsafeRunSync[T](name: String)
      (using logger: contextual.Logger)
        (implicit evidence$2: contextual.Executor[F], x$3: Task.this.Pretty[F]): Unit
  */

object Instances:
  val sName = "Thread-0"
  val bName = 110_000
  /*
    given initialization is on-demand if the given instance or clause has not type parameters
    different threads might create different instances for the same given definition
      for logger and pretty printer, the objects are create the first time they are accessed
    if given definition has type parameters, an instance is create for each reference
      as for Executor[T] for each T
  */
  given pretty as Printer.Printable[String] = Printer.Printable[String](sName) // given alias, only one type
  given [T <: Int] as Printer.Printable[Int] = Printer.Printable[Int](bName) // can be anonymous
  given [T] as Executor[T], Logger // anonymous given instances, compiler will synthesize a name
  /*
    If you're not that lazy and got cool names, you can write things as follows:
    given executor[T]: Executor[T]
    given executor[T]: Executor[T] = new Executor[T] {}
    given logger: Logger
    given logger: Logger = new Logger {}
    
    In general:
    given Int = 10  // Anonymous
    given x: String = "foo"  // Named
    given f(given x: Int): Option[Int] = Some(x * x)  // With given parameters
    given [T](given opt: Option[T]): List[T] = opt.toList  // Anonymous with type parameters
  */
object BoringMain:
  import Instances.{given Executor[?], given Logger, given Printer.Printable[Int]} // By-type imports
  /* 
    import Instances.given // will import all givens
    import Instances._ // will import everithing but given instances
    import Instances.{given, _} // will import everything
    import Instances.{sName, given} // is ok
    import Instances.{given, sName} // not ok: named imports cannot follow wildcard imports
  */
  lazy val boringTask = Task("task").unsafeRunSync[F = Int]("fascinating") // but I want a String printer

  /*
    for passing all parameters explicitly you have to especify what parameters are given
  */
  val executor: Executor[Int] = summon[Executor[Int]] // given by context bound
  val logger: Logger = summon[Logger]
  val printer: Printer.Printable[Int] = summon[Printer.Printable[?]]
  lazy val anotherTask = 
    Task("another task").unsafeRunSync[F = Int]("just")(using logger)(using executor, printer)

  /*
    given bindings are allowed anywhere a pattern is allowed: given _: T, given t: T
    nested given instances are significant for local coherence
  */
  lazy val someTask = 
    for
      given log: Logger <- Some(logger)
      given pretty: Printer.Printable[String] <- Some(Instances.pretty)
      _ <- Some(summon[Executor[String]].trace)
    yield
      pretty match
        case given _: Printer.Printable[String] => Task("some task").unsafeRunSync[F = String]("[String]")
end BoringMain