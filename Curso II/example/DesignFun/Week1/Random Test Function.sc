trait Generator[+T] {
  self =>
  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate: S = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  def generate = scala.util.Random.nextInt()
}

def single[T](x: T) = new Generator[T] {
  def generate = x
}

val booleans = integers.map(_ > 0)

def pairs[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = for {
  x <- t
  y <- u
} yield (x, y)

def emptyLists = single(Nil)

def nonEmptyLists = for {
  head <- integers
  tail <- lists
} yield head :: tail

def lists: Generator[List[Int]] = for {
  cutoff <- booleans
  list <- if (cutoff) emptyLists else nonEmptyLists
} yield list

def test[T](g: Generator[T], n: Int = 100)(test: T => Boolean): Unit = {
  for (i <- 0 until n) {
    val value = g.generate
    assert(test(value), "Test failded for " + value)
  }
  println("Passed " + n + "tests <3")
}


test(pairs(lists, lists)) {
  case (xs, ys) => (xs ++ ys).length > xs.length
}