trait Generator[+T] {
  self => // an alias for ”this”.
  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  def generate = scala.util.Random.nextInt()
}

def pairs[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = for {
  x <- t
  y <- u
} yield (x, y)

val booleans = integers.map(_ > 0)

def single[T](x: T): Generator[T] = new Generator[T] {
  def generate = x
}

def choose(lo: Int, hi: Int): Generator[Int] =
  for (x <- integers) yield lo + x % (hi - lo)

def oneOf[T](xs: T*): Generator[T] =
  for (idx <- choose(0, xs.length)) yield xs(idx)


object ListGenerator {
  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list

  def emptyLists: Generator[Nil.type ] = single(Nil)

  def nonEmptyLists: Generator[List[Int]] = for {
    head <- integers
    tail <- lists
  } yield head :: tail
}


object TreeGenerator {
  trait Tree

  case class Inner(left: Tree, right: Tree) extends Tree

  case class Leaf(x: Int) extends Tree


  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

  def inners: Generator[Inner] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)
}

object TestGenerator {
  def test[T](g: Generator[T], numTimes: Int = 100)
             (test: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
      assert(test(value), "test failed for " + value)
    }
    println("passed " + numTimes + " tests")
  }
}

ListGenerator.lists.generate

TreeGenerator.trees.generate

TestGenerator.test(pairs(ListGenerator.lists, ListGenerator.lists)) {
  case (xs, ys) => (xs ++ ys).length > xs.length
}