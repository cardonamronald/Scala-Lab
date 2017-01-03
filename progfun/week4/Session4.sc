import java.util.NoSuchElementException

trait List [+T] {
  def isEmpty : Boolean
  def head : T
  def tail : List[T]

  def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
}

class Cons [T] (val head : T, val tail : List[T]) extends List[T] {
  def isEmpty : Boolean = false
}

object Nil extends List[Nothing] {
  def isEmpty : Boolean = true

  def head : Nothing = throw new NoSuchElementException("Nil.head")

  def tail : Nothing = throw new NoSuchElementException("Nil.tail")
}



def nth [T] (n : Int, L : List[T]) : T = {

  def help (m : Int, L : List[T]) : T = {
    if (n == m) {
      if (!L.isEmpty) L.head else throw new IndexOutOfBoundsException("NIL LIST")
    }
    else help(m + 1, L.tail)
  }
  help(0, L)
}

object List {
  def apply [T] : List[T] = Nil
  def apply[T]  (x1 : T): List[T] = new Cons[T](x1, Nil)
  def apply [T] (x1 : T, x2 : T) : List[T] = new Cons[T](x1, new Cons[T](x2, Nil))
}


