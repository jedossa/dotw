package contextual

import org.junit.Test
import org.junit.Assert.assertEquals

class MonoidTest

  @Test def derivation: Unit =
    
    case class LLC(name: String, net: Int) derives Monoid

    val fusion = LLC("A", 15) |+| LLC("B", 5)
    val expected = LLC("AB", 20)

    assertEquals(fusion.name, expected.name)
    assertEquals(fusion.net, expected.net)