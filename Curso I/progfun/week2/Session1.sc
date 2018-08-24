def iter(f : Int => Int) (g : (Int, Int) => Int) (a : Int, b : Int, baseCase : Int) : Int = {
  if (a > b) baseCase
  else g(f(a), iter (f) (g) (a + 1, b, baseCase))
}

def factorial (a : Int) : Int =  iter (x => x) (times) (1 ,a , 1)

def times (a : Int, b : Int) : Int = a * b
def plus (a : Int, b : Int) : Int =  a + b


def sum(f: Int => Int, a: Int, b: Int): Int = {
  if (a > b) 0
  else f(a) + sum(f, a + 1, b)
}

def id(x: Int): Int = x
def cube(x: Int): Int = x * x * x
def fact(x: Int): Int = if (x == 0) 1 else x * fact(x-1)

def sumInts(a: Int, b: Int): Int = sum(id, a, b)
def sumCubes(a: Int, b: Int): Int = sum(cube, a, b)
def sumFact(a: Int, b: Int): Int = sum(fact, a, b)

sumInts(0, 100)
sumCubes(0, 10)
sumFact(0, 5)

sum(x => x, 0, 100)
sum(x => x * x * x, 0, 10)


def sum2(f: Int => Int)(a: Int, b: Int): Int = {
  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop(a + 1, f(a) + acc)
  }
  loop(a, 0)
}

sum2(cube)(0, 10)