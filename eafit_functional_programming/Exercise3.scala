package co.edu.eafit.dis.progfun.scala.intro

object Exercise3 extends App {
  def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(a, a % b)

  def factorial(n: Int): Int =
    if(n == 0) 1 else n * factorial(n - 1)

  def tailRecFactorial(n: Int): BigInt = {
    def loop(acc: BigInt, n: Int): BigInt =
      if (n == 0) acc else loop(acc * n, n - 1)

    loop(1, n)
  }
}
