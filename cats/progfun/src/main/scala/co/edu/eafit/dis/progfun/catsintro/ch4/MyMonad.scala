package co.edu.eafit.dis.progfun.catsintro.ch4

trait MyMonad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    // Meto en la caja, aplico la función y le paso el valor :)
    flatMap(value)(a => pure(func(a)))

  def map1[A, B](value: F[A])(func: A => B): F[B] =
    // Paso el valor, le aplico la funcion y lo meto en la caja :)
    flatMap(value)(func andThen pure)
}

object MyMonad {}
