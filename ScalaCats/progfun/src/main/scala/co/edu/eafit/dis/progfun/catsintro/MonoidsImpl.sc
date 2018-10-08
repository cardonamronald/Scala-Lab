//Exercise
trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] =
    monoid
}

object BooleanMonoids {
  implicit val booleanAndMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      override def empty: Boolean = true
      override def combine(x: Boolean, y: Boolean): Boolean = x && y
    }

  implicit val booleanOrMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      override def empty: Boolean = false
      override def combine(x: Boolean, y: Boolean): Boolean = x || y
    }

  implicit val booleanXorMonoid: Monoid[Boolean] = //Either Monoid
    new Monoid[Boolean] {
      override def empty: Boolean = false
      override def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (!x && y)
    }

  implicit val booleanNorMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      override def empty: Boolean = true
      override def combine(x: Boolean, y: Boolean): Boolean = !((x && !y) || (!x && y))
    }
}

//Monoids composition
implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
  def combine(a: Int, b: Int) = a + b
  def empty = 0
}

object setMonoids {
  implicit def setUnionMonoid[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      override def empty: Set[A] = Set.empty[A]
      override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    }

  implicit val intSetMonoid: Monoid[Set[Int]] = Monoid[Set[Int]]
}