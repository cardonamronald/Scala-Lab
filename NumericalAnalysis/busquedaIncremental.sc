import scala.math.abs

val tolerancia = 1e-12
def isCloseEnough(x: Double, y: Double): Boolean =
  if (x == 0.0) {print("Divide by zero"); false} else
    abs((x - y) / x) / x < tolerancia

def incrementalSearch (x0: Double, delta: Double, niter: Int, f: Double => Double): (Double, Double) = {
  val fx0: Double = f(x0)
  if (fx0 == 0) (x0, x0) else {
    val x1: Double = x0 + delta
    val fx1: Double = f(x1)
    if ((fx0 * fx1 > 0) && niter >= 0) incrementalSearch(x1, delta, niter + 1, f)

    if(fx1 == 0) (x0, x0)
    else if (fx0 * fx1 < 0) (x0, x1)
    else (-1.111111,-1.1111111)
  }
}

incrementalSearch(-10, 1, 30,
                    x => math.exp((3 * x) - 12) + x * math.cos(3  * x) - math.pow(x, 2) + 4)