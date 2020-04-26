package co.edu.eafit.dis.progfun.catsintro.ch4

trait MyMonad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(a => pure(func(a))) // Paso el valor, le aplico la funcion y lo meto en la caja :)

  def _map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(func andThen pure) // Paso el valor, le aplico la funcion y lo meto en la caja :)
}

object MyMonad {}
