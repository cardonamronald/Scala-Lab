package co.edu.eafit.dis.progfun.catsintro.ch2.monoid.instances

import co.edu.eafit.dis.progfun.catsintro.ch2.monoid.Monoid

object SetMonoids {
  implicit def setUnionMonoid[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      override def empty: Set[A]                         = Set.empty[A]
      override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    }

  implicit val intSetMonoid: Monoid[Set[Int]] = Monoid[Set[Int]]
}
