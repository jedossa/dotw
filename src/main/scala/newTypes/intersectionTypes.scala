package newTypes

/*
  The type A & B represents values that are of the type A and B at the same time
*/
trait Energy:
  def getLevel: Int
trait Time:
  def hasToSpare: Boolean
trait Awesomeness

/*
  here the motivation parameter would be equivalent to:
  trait Motivation extends Energy with Time
*/
def doFloos(motivation: Energy & Time): Awesomeness =  // top level def
  def createPR = ???
  def getSomeRest = ???
  if (motivation.hasToSpare && motivation.getLevel > 0) createPR
  else getSomeRest

/*
  If a member appears in both A and B, 
  its type in A & B is the intersection of its type in A and its type in B
*/
trait X:
  def replicas: List[X]
trait Y:
  def replicas: List[Y]

class Z extends X with Y:
  def replicas = List.empty //what's the type ?

/*
  & is commutative: A & B <: B & A
  For a type constructor T[?]
  if T is covariant T[+?], T[A] & T[B] ~> T[A & B]
  if T is contravariant T[-?], T[A] & T[B] ~> T[A | B]
*/
val xyz = Z()
// Let's set the types to show that & and its simplifications are commutative 
val replicas1 = xyz.replicas
val replicas2 = xyz.replicas
val replicas3 = xyz.replicas
val test: Boolean = replicas1 == replicas2 && replicas1 == replicas3
