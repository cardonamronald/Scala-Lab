abstract class IntSet {
  def incl (x : Int) : IntSet
  def contains (x : Int) : Boolean
  def union (other : IntSet) : IntSet
}

class Empty extends IntSet {
  def contains (x : Int) : Boolean = false
  def incl (x : Int) : IntSet = new NonEmpty(x, new Empty, new Empty)

  def union (other : IntSet) : IntSet = other

  override def toString = "."
}

class NonEmpty(elem : Int, right : IntSet, left : IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  def incl(x: Int): IntSet = {
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  }

  def union (other : IntSet) : IntSet = {
    ((left union right) union other) incl elem
  }

  override def toString = "{" + left + elem + right + "}"
}

object Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  def union (other: IntSet) : IntSet = Empty
}