import scala.annotation.tailrec

//This method removes the element at index n.

def removeAt[T] (n : Int, xs : List[T]) : List[T] = {
  @tailrec
  def rec [T] (n : Int, xs : List[T], ys: List[T]): List[T] = {
    if (n == 0) xs ::: ys.tail
    else rec(n - 1, xs ::: List(ys.head), ys.tail)
  }
  if (n >= xs.length) xs else {
    rec(n, List(), xs)
  }
}
removeAt(1, List('a', 'b', 'c', 'd'))

def init[T](xs: List[T]): List[T] = xs match {
  case Nil => throw new Error("init of empty list")
  case List(x) => Nil
  case y :: ys => y :: init(ys)
}
init(List(1, 2, 3, 4, 5, 6, 7, 8))

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case Nil => ys
  case z :: zs => z :: concat(zs, ys)
}
concat(List(1, 2, 3), List(4, 5, 6))

def reverse[T](xs: List[T]): List[T] = xs match {
  case Nil => xs
  case y :: ys => reverse(ys) ++ List(y)
}
reverse(List(1, 2, 3, 4))
