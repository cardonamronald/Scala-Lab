val a = new Rationals(1,3)
val b = new Rationals(5,7)
val c = new Rationals(3,2)

a.sub(b).sub(c).toS
class Rationals (x : Int, y : Int) {

  require(y != 0, "Denominator = 0")

  def this (x : Int) = this (x, 1) //Constructor secundario
  def numer = x
  def denom = y

  def add (s : Rationals) : Rationals = {
    new Rationals(numer * s.denom + s.numer * denom, denom * s.denom)
  }

  def sub (s : Rationals) : Rationals = add(s.neg)

  def neg : Rationals = new Rationals(numer * -1, denom)

  def toS = println(denom + '/' + numer)
}


def makeString (x : Rationals) = {
    x.numer + '/' + x.denom
  }

  def addRationals (r : Rationals, s : Rationals) : Rationals = {
    new Rationals((r.numer * s.denom) + (s.numer * r.denom), r.denom * s.denom)
  }

  def subRationals (r : Rationals, s : Rationals) : Rationals = {
    new Rationals((r.numer * s.denom) - (s.numer * r.denom), r.denom * s.denom)
  }

  def timesRationals (r : Rationals, s : Rationals) : Rationals = {
    new Rationals(r.numer * s.numer, r.denom * s.denom)
  }

  def divRationals (r : Rationals, s : Rationals) : Rationals = {
    new Rationals(r.numer * s.denom, r.denom * s.numer)
  }

  def compRationals (r : Rationals, s : Rationals) : Boolean = {
    r.numer * s.denom == r.denom * s.numer
  }
