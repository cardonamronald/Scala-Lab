package co.edu.eafit.dis.progfun.scala.intro

object Exercise8 extends App {
  def removeAt[T](xs: List[T], n: Int): List[T] =
    (xs take n) ::: (xs drop n + 1)

  println(removeAt(List("orange", "apple", "banana", "potato"), 1))
  println(removeAt(List(0, 1, 2, 3, 4), 1))
}
