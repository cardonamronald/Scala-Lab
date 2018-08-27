import math.abs

val tolerancia = 1e-12
def isCloseEnough(x: Double, y: Double): Boolean =
  abs((x - y) / x) / x < tolerancia

def fixedPoint(f: Double => Double)(firstGuess: Double) = {
  def iterate(guess: Double): Double = {
    val next = f(guess)
    println(next)
    if(isCloseEnough(guess, next)) next
    else iterate(next)
  }
  iterate(firstGuess)
}
fixedPoint(x => 1 + x/2)(1)

def sqrt(x: Double): Double = fixedPoint(averageDamp(y => x / y))(1)

//AverageDamp is a function that receives a function and returns another function
def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

sqrt(81)