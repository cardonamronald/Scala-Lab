import math.abs

val tolerance = 0.0001
def isGoodEnough (x : Double, y : Double) = {
  abs((x - y) / x) / x < tolerance
}

def fixedPoint (f : Double => Double) (firstGuess: Double) = {
  def iterate (guess : Double) : Double = {
    val next = f(guess)
    if(isGoodEnough(guess, next)) next
    else
      iterate(next)
  }
  iterate(firstGuess)
}

def sqrt (x: Double) = fixedPoint (averageDamp(y => x / y))(1.0)

sqrt(2)

def averageDamp(f : Double => Double) (x : Double) = (x + f(x)) / 2

