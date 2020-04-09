package co.edu.eafit.dis.progfun.catsintro.ch4

import cats.Monad

// F is the type of the Monad
// E is the type of error contained within F

trait MonadError[F[_], E] extends Monad[F] {

  // Lift an error into the monad context
  def raiseError[A](e: E): F[A]

  // Handle an error, potentially recovering from it
  def handleError[A](fa: F[A])(f: E => A): F[A]

  // Test an instance of 'F', failing if the predicate is NOT satisfied
  def ensure[A](fa: F[A])(e: E)(f: A => Boolean): F[A]
}