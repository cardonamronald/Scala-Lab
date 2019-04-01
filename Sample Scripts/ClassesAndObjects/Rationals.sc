val x = new Rationals(1,3)
val y = new Rationals(5,7)
val z = new Rationals(3,2)
(x - y) - z
y + y
x < y
x max y

class Rationals (x : Int, y : Int) {

  require(y != 0, "Denominator must be non zero")

  def this (x : Int) = this (x, 1) //Constructor secundario
  private def g: Int = gcd(x, y)
  def numer: Int = x / g
  def denom: Int = y / g


  def + (s : Rationals) : Rationals = {
    new Rationals(numer * s.denom + s.numer * denom, denom * s.denom)
  }

  def - (s : Rationals) : Rationals = this + (~s)

  def unary_~ : Rationals = new Rationals(-numer, denom)

  def < (that: Rationals): Boolean = this.numer * that.denom < this.denom * that.numer

  def max(that: Rationals): Rationals = if (this < that) that else this

  override def toString: String = denom + "/" + numer

  private def gcd(a: Int, b: Int): Int =
    if(b == 0) a else gcd(b, a % b)
}


def makeString (x : Rationals) = {
  x.numer + "/" + x.denom
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
