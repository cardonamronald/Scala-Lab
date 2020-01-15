package co.edu.eafit.dis.progfun.catsintro.ch2.monoid.instances

import co.edu.eafit.dis.progfun.catsintro.ch2.monoid.Monoid

object BooleanMonoids {
  implicit val booleanAndMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      override def empty: Boolean                           = true
      override def combine(x: Boolean, y: Boolean): Boolean = x && y
    }

  implicit val booleanOrMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      override def empty: Boolean                           = false
      override def combine(x: Boolean, y: Boolean): Boolean = x || y
    }

  implicit val booleanXorMonoid: Monoid[Boolean] = //Either Monoid
    new Monoid[Boolean] {
      override def empty: Boolean                           = false
      override def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (!x && y)
    }

  implicit val booleanNorMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      override def empty: Boolean                           = true
      override def combine(x: Boolean, y: Boolean): Boolean = !((x && !y) || (!x && y))
    }
}
