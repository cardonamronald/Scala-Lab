package co.edu.eafit.dis.progfun.catsintro.ch2.monoid

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid
}
