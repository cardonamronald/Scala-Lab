package co.edu.eafit.dis.progfun.catsintro.ch2.monoid

trait Semigroup[A] {
  def combine(x: A, y: A): A
}
