package newTypes

/*
  A match type reduces to one of a number of right hand sides, depending on a scrutinee type
*/
type Succesor[N <: Int] <: Int = N match //succesor of a literal Int
  case 0 => 1
  case 1 => 2
  case 2 => 3
  case 3 => 4
  case 4 => 5
  // so on ...

type Zero = 0
type One = Succesor[0]
val one: One = 1
val two: Succesor[One] = 2
val three: Succesor[Succesor[Succesor[Zero]]] = 3
type Peano = [N <: Int] =>> Zero | Succesor[N]
