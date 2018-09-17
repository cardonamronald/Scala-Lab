object pairs {
  def isPrime(n: Int): Boolean = (2 until n).forall(x => n % x != 0)
  val n = 7
  (1 until(n) map(i =>
    (1 until(i)) map(j => (i, j)))).flatten
    .filter(x => isPrime(x._1 + x._2))

  for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i + j)
  } yield (i, j)
}