package co.edu.eafit.dis.progfun.scala.intro

object Exercise10 extends App {

  def mapFun[T, U](xs: List[T], func: T => U): List[U] = {
    xs.foldRight(List[U]())((y, ys) => func(y) :: ys)
  }

  def lengthFun[T](xs: List[T]): Int =
    xs.foldRight(0)((_, l) => l + 1)
}
