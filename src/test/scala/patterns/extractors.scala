package patterns

import org.junit.Test
import org.junit.Assert.assertEquals

/*
  Extractors are objects that expose a method unapply or unapplySeq
*/
class ExtractorsTest

  opaque type Even = Int
  object Even
    def unapply(i: Int): Boolean = i % 2 == 0

  @Test def booleanMatch: Unit =
    def isEven: Int => Boolean =
      case e @ Even() => e % 2 == 0
      case _ => false

    def isOdd: Int => Boolean = !isEven(_)

    assert(isEven(4))
    assert(isOdd(9))
  
  @Test def productMatch: Unit =
    case class Wrapper(value: String)
    object Wrapper
      def unapply(value: String): Wrapper = Wrapper(value)

    "value" match
      case Wrapper(value) => assertEquals(value, "value")

  @Test def singleMatch: Unit =
    class Nat(val nat: Int)
      def get: Int = nat
      def isEmpty: Boolean = nat < 0

    object Nat
      def unapply(nat: Int): Nat = Nat(nat)

    def isNat: Int => Boolean =
      case Nat(n) => true
      case _      => false

    assert(isNat(5))
    assert(!isNat(-11))

  @Test def sequenceMatch: Unit =
    object Sequence
      def unapplySeq(fullName: String): Option[List[String]] =
        val names = fullName.split(" ")
        if (names.size < 2) None
        else Some(names.head :: names.last :: names.drop(1).dropRight(1).toList)
    
    "Daenerys Stormborn of the House Targaryen" match
      case Sequence(firstName, lastName, titles : _*) =>
        assertEquals("Daenerys Targaryen", s"$firstName $lastName")
        assertEquals("Stormborn of the House", titles.mkString(" "))
      case _ => assert(false)

  @Test def productSequenceMatch: Unit =
    case class Folder(name : String, subfolders: Folder*)
    object Empty extends Folder("", null)
    object Folder
      def unapplySeq(f: Folder): Option[(String, Seq[Folder])] = Some(f.name, f.subfolders)
    
    Folder("/", Folder("~", Empty)) match
      case Folder(root, Folder(home, subfolders : _*), _ : _*) => 
        assertEquals(root, "/")
        assertEquals(home, "~")
        assertEquals(subfolders, Empty :: Nil)
      case _ => assert(false)
