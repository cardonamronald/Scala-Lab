def iter(f : Int => Int) (g : (Int, Int) => Int) (a : Int, b : Int, baseCase : Int) : Int = {
  if (a > b) baseCase
  else g(f(a), iter (f) (g) (a + 1, b, baseCase))
}

def factorial (a : Int) : Int =  iter (x => x) (times) (1 ,a , 1)

def times (a : Int, b : Int) : Int = a * b
def plus (a : Int, b : Int) : Int =  a + b
