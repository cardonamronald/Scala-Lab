//Full parameterized mergeSort

import math.Ordering
def msort [T] (xs : List[T])(implicit ord : Ordering[T]) : List[T] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst),  msort(snd))
  }
}


def merge [T] (xs : List[T], ys : List[T]) (implicit ord : Ordering[T]) : List[T] = (xs, ys) match {
  //case (List(x), List()) => xs
  //case (List(), List(y)) => ys
  //case (List(x), List(y)) => if (xs.head < ys.head) xs ::: ys else ys ::: xs
  case (x :: xs1, List()) => xs
  case (List(), y :: ys1) => ys
  case (x :: xs1, y :: ys1) => if (ord.lt(x, y)) x :: merge(xs1, ys)(ord) else y :: merge(xs, ys1)(ord)
}

msort(List(30,26,52,6,6,6,5,66,-1,43,4654,4564,0))
msort(List("Ronald", "Cardona", "Martinez"))



