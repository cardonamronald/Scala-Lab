def insert (x : Int, xs : List[Int]) : List[Int] = xs match {
  case List() => List(x)
  case y :: ys => if (x <= y) x :: xs else y :: insert(x, ys)
}

def isort(xs: List[Int]): List[Int] = xs match {
  case List() => xs
  case y :: ys => insert(y, isort(ys))
}

isort(34 :: 4 :: 43 :: 345 :: -12 :: 23 :: 1 :: 0 :: Nil)