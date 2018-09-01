def incrementalSearch (x0: Double, delta: Double, niter: Int, f: Double => Double): (Double, Double) = {
  val fx0: Double = f(x0)
  if (fx0 == 0) (x0, x0) else {
    val x1: Double = x0 + delta
    val fx1: Double = f(x1)

    if(fx1 == 0) (x0, x0)
    else if (fx0 * fx1 < 0) (x0, x1)
    else if ((fx0 * fx1 > 0) && niter >= 0) incrementalSearch(x1, delta, niter - 1, f)
    else (-1.111111, -1.1111111) //Method Failed
  }
}

incrementalSearch(-10, 1, 30,
                    x => math.exp((3 * x) - 12) + x * math.cos(3  * x) - math.pow(x, 2) + 4)
incrementalSearch(-6, 0.001, 2100,
                    x => math.exp(2-(6*x)) * (x * math.cos(3*x)) + (4 * x) - 3)

val f: Double => Double = x => math.exp(2 - (6 * x)) * (x * math.cos(3 * x)) + (4 * x) - 3
f(incrementalSearch(-10, 1, 21, f)._1)
f(incrementalSearch(-10, 1, 21, f)._2)

val g: Double => Double =
  x => math.exp((3 * x) - 12) + x * math.cos(3  * x) - math.pow(x, 2) + 4

g(2.3701171875)
g(2.36947525991665)
