def msort (xs : List[Int]) : List[Int] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst),  msort(snd))
  }
}


def merge (xs : List[Int], ys : List[Int]) : List[Int] = (xs, ys) match {
  //case (List(x), List()) => xs
  //case (List(), List(y)) => ys
  //case (List(x), List(y)) => if (xs.head < ys.head) xs ::: ys else ys ::: xs
  case (x :: xs1, List()) => xs
  case (List(), y :: ys1) => ys
  case (x :: xs1, y :: ys1) => if (x < y) x :: merge(xs1, ys) else y :: merge(xs, ys1)
}

msort(List(30,26,52,6,6,6,5,66,43,4654,4564,0))

