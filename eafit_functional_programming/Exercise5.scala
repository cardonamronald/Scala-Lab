package co.edu.eafit.dis.progfun.scala.intro

object Exercise5 extends App {
  def factorial(x: Int): Int = if (x <= 1) 1 else x * factorial(x - 1)

  def sum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a + 1, b)
    sumF
  }

  def sumInts: (Int, Int) => Int = sum(x => x)
  def sumCubes: (Int, Int) => Int = sum(x => x * x * x)
  def sumFactorials: (Int, Int) => Int = sum(factorial)

  /** Exercise
    * 1. Write a product function that calculates the product of the
    * values of a function for the points on a given interval.
    * 2. Write factorial in terms of product.
    * 3. Can you write a more general function, which generalizes both
    * sum and product?
    **/

  def product(f: Int => Int)(a: Int, b: Int): Int = if (a > b) 1 else f(a) * product(f)(a + 1, b)

  def factorialInTermsOfProduct(n: Int): Int = product(x => x)(1, n)

  def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int =
    if(a > b) zero else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))

  mapReduce(x => x, (x: Int, y: Int) => x * y, 1)(1, 5)
  mapReduce(x => x, (x: Int, y: Int) => x + y, 0)(0, 100)

  def prod(f: Int => Int)(a: Int, b: Int): Int =
    mapReduce(f, (x, y) => x * y, 1)(a, b)
}