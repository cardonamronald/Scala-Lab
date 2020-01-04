package co.edu.eafit.dis.progfun.scala.intro

object Exercise7 extends App {
  val fruit = List("apples", "oranges", "pears")
  val nums = List(1, 2, 3, 4)
  val diag3 =
    List(
      List(1, 0, 0),
      List(0, 1, 0),
      List(0, 0, 1)
    )
  val empty = List()

  def isort(xs: List[Int]): List[Int] =
    if (xs.isEmpty) List.empty
    else insert(xs.head, isort(xs.tail))

  def insert(x: Int, xs: List[Int]): List[Int] =
    if (xs.isEmpty || x <= xs.head) x :: xs
    else xs.head :: insert(x, xs.tail)


  def insertionSort(list: List[Int]): List[Int] = list match {
    case x :: xs => insertion(x, insertionSort(xs))
    case Nil => Nil
  }

  def insertion(x: Int, xs: List[Int]): List[Int] = xs match {
    case Nil => x :: xs
    case y :: ys => if(x <= y) x :: xs else y :: insertion(x, ys)
  }

  print(insertionSort(List(9, 8, 7, 6, 5, 4, 3, 2, 1)))

  print(isort(List(9, 8, 7, 6, 5, 4, 3, 2, 1)))
}