import scala.math.abs

val tolerancia = 0.5e-3
def isCloseEnough(x: Double, y: Double): Boolean =
  if (x == 0.0) {print("Divide by zero"); false} else
    abs((x - y) / x) / x < tolerancia

