package co.edu.eafit.dis.progfun.catsintro.ch4

object Id extends App {
  import cats.Id

  def pure[A](value: A): Id[A] = value

  def map[A, B](initial: Id[A])(func: A => B): Id[B] = func(initial)

  def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] = func(initial)

  println(pure(0))
  println(map(0)(_ + 1))
  println(flatMap(Id.pure(0))(_ + 1))
}
