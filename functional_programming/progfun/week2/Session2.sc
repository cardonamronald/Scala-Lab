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




def id(x: Int): Int = x
def cube(x: Int): Int = x * x * x
def fact(x: Int): Int = if (x == 0) 1 else x * fact(x-1)

def sum(f: Int => Int): (Int, Int) => Int = {
  def sumF(a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a) + sumF(a + 1, b)
  }
  sumF
}

sum(cube)(0, 10)

def f = sum(cube)
f(10, 20)

def prettySum(f: Int => Int)(a: Int, b: Int): Int = {
  if (a > b) 0 else f(a) + prettySum(f)(a + 1, b)
}

def product(f: Int => Int)(a: Int, b: Int): Int = {
  if(a > b) 1
  else f(a) * product(f)(a + 1, b)
}

def fatorial(n: Int): Int = {
  if (n == 0) 1
  else product(x => x)(1, n)
}
fatorial(5)


def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
    if(a > b) zero
    else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))
}

def product1(f: Int => Int)(a: Int, b: Int): Int =
  mapReduce(f, (x, y) => x * y, 1) (a, b)

product1(x => x)(1, 5)