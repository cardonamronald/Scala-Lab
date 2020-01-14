import cats.instances.int._
import cats.syntax.semigroup._
import cats.Monoid
  // Un type class es una solucion estandar para un problema recurrente, ejemplo combinar cosas
  // L@s type classes tienen herencia en scala, monoid es una subclase de semigroup

  implicitly[Monoid[Int]].combine(1, 2)

  def add(items: List[Int]): Int =
  items.foldLeft(Monoid[Int].empty)(_ |+| _)

  implicit def OptionMonoid[T](implicit TMonoid: Monoid[T]): Monoid[Option[T]] =
    new Monoid[Option[T]] {
      override val empty: Option[T] = None

      override def combine(e1: Option[T], e2: Option[T]): Option[T] = (e1, e2) match {
        case(Some(a), Some(b)) => Some(TMonoid.combine(a, b))
        case(Some(a), None) => Some(a)
        case(None, Some(b)) => Some(b)
        case(None, None) => None
      }
    }