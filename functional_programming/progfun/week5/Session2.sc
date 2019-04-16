def msort (xs : List[Int]) : List[Int] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge (msort(fst),  msort(snd))
  }
}


def merge(xs : List[Int], ys : List[Int]) : List[Int] = (xs, ys) match {
  //case (List(x), List()) => xs
  //case (List(), List(y)) => ys
  //case (List(x), List(y)) => if (xs.head < ys.head) xs ::: ys else ys ::: xs
  case (x :: xs1, List()) => xs
  case (List(), y :: ys1) => ys
  case (x :: xs1, y :: ys1) => if (x < y) x :: merge(xs1, ys) else y :: merge(xs, ys1)
}

msort(List(30,26,52,6,6,6,5,66,43,4654,4564,0))
val pair = ("answer", 42)

def merge1(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
  case (Nil, Nil) => Nil
  case (Nil, ys1) => ys
  case (xs1, Nil) => xs
  case (x1 :: xs1, y1 :: ys1) => if (x1 < y1) x1 :: merge1(xs1, ys) else y1 :: merge(xs, ys1)
}
val test = (List(1, 3, 5, 7, 9, 11), List(2, 4, 6, 8, 10, 12))
if (merge(test._1, test._2) == merge1(test._1, test._2)) "Yaaaaas" else ":("

msort(List(1, 23, 4, 56, 344, 3, 4, -3, 5, -8776543, 4,4, 0))