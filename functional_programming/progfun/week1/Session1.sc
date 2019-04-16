//Square Root with the Newton Method

def abs (x : Double) = if (x > 0) x else -x

def square (x : Double) = x * x

def apr (ant : Double, act : Double) =
  abs (square(ant) - act) / act < 0.0000000001

def improve (x : Double, y : Double) =
  (x + y / x) / 2

def sqrtIter (x : Double, y : Double): Double =
  if (apr(x , y)) x else sqrtIter (improve(x, y), y)

def sqrt (double: Double) = sqrtIter(1.0, double)

sqrt (1e-6)
sqrt(5)