package enums

import scala.util.Try

/*
  An enumeration is used to define a type consisting of a set of named values
*/
enum Currency(symbol : String)
  def name: String = toString
  def getSymbol: String = symbol

  case USD extends Currency("$")
  case EUR extends Currency("€")
  case JPY extends Currency("¥")
  /*
    enum's methods
    Currency.USD.ordinal == 0
    Currency.USD.name == "USD"
    Currency.values == Array(USD, EUR, JPY)
    CurrenyE.valueOf("EUR") == EUR // IllegalArgumentException: key not found
  */

object Currency
  export Maybe.toMaybe
  def (from: Currency) tradeTo (to: Currency): Currency = to
  def safeValueOf(value: String): Maybe[Currency] = Try(Currency.valueOf(value)).toMaybe

/*
  Check java interop at https://github.com/lampepfl/dotty/tree/master/tests/run/enum-java
*/
