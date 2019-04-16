object TestSequences {
  val xs = Array(1, 2, 3, 44)
  xs map (x => x * 2)

  val s = "Hello World!"
  s filter (x => x.isUpper)

  def combinations(M: Int, N: Int) =
    (1 to M) flatMap (x => (1 to N) map (y => (x, y)))

  combinations(5, 8)

  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
    (xs zip ys).map(x => x._1 * x._2).sum

  def isPrime(n: Int): Boolean = (2 until n).forall(x => n % x != 0)
}