package newTypes

import Currency._
/*
A union type A | B has as values all values of type A and also all values of type B.
*/
trait Currency(symbol: Symbol) // trait parameter
  def name: String
  def getSymbol: Symbol = symbol
case object USD extends Currency("$".toSymbol)
  def name: String = "Dolar"
case object EUR extends Currency("€".toSymbol)
  def name: String = "Euro"
case object JPY extends Currency("¥".toSymbol)
  def name: String = "Yen"

object Currency
  def (s: String) toSymbol: Symbol = Symbol(s) // extension method
  opaque type Symbol = String
  object Symbol
    def apply(symbol: String): Symbol = symbol

  type USD = USD.type
  type EUR = EUR.type
  type JPY = JPY.type

  def (from: Currency) tradeTo (to: Currency): Currency = to 

  def payLocal: (EUR | USD) => String = // payLocal(JPY)
    case c: USD => tradeTo(c)(EUR).getSymbol
    case c: EUR => c.getSymbol // exhaustive match
  //  case c: JPY.type => c.getSymbol

  def default(isEU: Boolean): Currency & Product & Serializable = if(isEU) EUR else USD
  // can we get an union type as return type instead of a intersection type?

  def rate(c: EUR | USD | JPY): Int = c match
    case _: EUR | USD => 1
    // case (_: USD) | EUR => 2 // unreachable case
    // case _: (USD | EUR) => 3 // unreachable case
    case _ => 0

  /*
      | is commutative and associative: A | B =:= B | A ; A | (B | C) =:= (A | B) | C
      A, B <: A | B
      & is distributive over | : A & (B | C) =:= A & B | A & C
  */

  // Members of a union type join (Join type: the smallest intersection type of base classes)
  def join(union: EUR | USD): String = union.name // what's the join type ?
end Currency

// (Co)Products
type Vowel = "a" | "e" | "i" | "o" | "u" // literal type
type Digit = 1 | 2  | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 0
class Basic(v: Vowel, d: Digit) // Basic("a", 0) no boxing overhead, also `new` is optional 

// implement `Either` as a union type and use it for creating a safe division fucntion