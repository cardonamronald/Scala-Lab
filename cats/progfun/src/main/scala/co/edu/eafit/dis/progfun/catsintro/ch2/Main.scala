package co.edu.eafit.dis.progfun.catsintro.ch2

import co.edu.eafit.dis.progfun.catsintro.ch2.monoid.Monoid

object Main extends App {
  def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) ==
      m.combine(m.combine(x, y), z)
  }

  def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) &&
    (m.combine(m.empty, x) == x)
  }

  import co.edu.eafit.dis.progfun.catsintro.ch2.monoid.instances.BooleanMonoids.booleanXorMonoid

  assert(associativeLaw(true, false, true))
  assert(identityLaw(true))
}
