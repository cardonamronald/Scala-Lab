import scala.annotation.tailrec

//This method removes the element at index n.

def removeAt[T] (n : Int, xs : List[T]) : List[T] = {

  @tailrec
  def rec [T] (n : Int, xs : List[T], ys: List[T]) = {
    if (n == 0) xs ::: ys.tail
    else rec (n - 1, xs ::: List(ys.head), ys.tail)
  }
  if (n >= xs.length) xs else {
    rec(n, List(), xs)
  }
}

removeAt(1, List('a', 'b', 'c', 'd'))


