package co.edu.eafit.dis.progfun.catscases
import cats.Semigroup
import cats.syntax.semigroup._
import cats.syntax.either._

/**
  * - [ ] Combine checks
  * - [ ] Accumulating errors as we check
  * - [ ] Transforming data as we check it
  **/
object DataValidation extends App {
  final case class Check[E, A](func: A => Either[E, A]) {
    def apply(value: A): Either[E, A] = func(value)

    def and(that: Check[E, A]) (implicit s: Semigroup[E]): Check[E, A] = {
      Check { a =>
        (this (a), that(a)) match {
          case (Left(e1), Left(e2)) => (e1 |+| e2).asLeft
          case (Left(e), Right(a1)) => e.asLeft
          case (Right(a1), Left(e)) => e.asLeft
          case (Right(a1), Right(a2)) => a.asRight
        }
      }
    }
  }


}
