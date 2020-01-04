package co.edu.eafit.dis.progfun.scala.intro

object Exercise9 extends App {
  def squareList(xs: List[Int]): List[Int] = xs match {
    case Nil => Nil
    case y :: ys => y * y :: squareList(ys)
  }

  def squareListWithMap(xs: List[Int]): List[Int] =
    xs map(x => x * x)

  println(squareList(List(1, 2, 3, 4, 5)))
  println(squareListWithMap(List(1, 2, 3, 4, 5)))
}