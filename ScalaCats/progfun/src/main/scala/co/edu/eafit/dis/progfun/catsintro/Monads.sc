def parseInt(str: String): Option[Int] =
  scala.util.Try(str.toInt).toOption

def divide(a: Int, b: Int): Option[Int] =
  if(b == 0) None else Some(a / b)

//If the result is Some the flatMap method will append the next computation
//else they will return None. Because parseInt and divide return Options
def stringDivideBy(aStr: String, bStr: String): Option[Int] =
  parseInt(aStr).flatMap { aNum =>
    parseInt(bStr).flatMap { bNum =>
      divide(aNum, bNum)
    }
  }

//Every Monad is also a Functor, so we have the map method and we are able
//to use for-comprehensions
def strDivideBy(aStr: String, bStr: String): Option[Int] =
  for {
    aNum <- parseInt(aStr)
    bNum <- parseInt(bStr)
    ans <- divide(aNum, bNum)
  } yield ans

strDivideBy("44", "2")
stringDivideBy("44", "2")

//Lists as sets of intermediate results
//Flatmap calculate permutations and combinations over that lists
for {
  x <- (1 to 3).toList
  y <- (4 to 5).toList
} yield (x, y)

//Future is a monad that sequences computations without worrying that they
//are asynchronous:
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
def doSomethingLongRunning: Future[Int] = ???
def doSomethingElseLongRunning: Future[Int] = ???

def _doSomethingVeryLongRunning: Future[Int] =
  for {
    result1 <- doSomethingLongRunning
    result2 <- doSomethingElseLongRunning
  } yield result1 + result2

def doSomethingVeryLongRunning: Future[Int] =
  doSomethingLongRunning.flatMap { result1 =>
    doSomethingElseLongRunning.map { result2 =>
      result1 + result2
    }
  }

//Map function on Monad
import scala.language.higherKinds
//Pure -> Constructor

trait MyMonad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(a => pure(func(a)))
}

//Monads in Cats
import cats.Monad
import cats.instances.option._ // for Monad
import cats.instances.list._ // for Monad

val opt1 = Monad[Option].pure(3)

val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))

val opt3 = Monad[Option].map(opt2)(a => 100 * a)

val list1 = Monad[List].pure(3)

val list2 = Monad[List].
  flatMap(List(1, 2, 3))(a => List(a, a*10))

val list3 = Monad[List].map(list2)(a => a + 123)

//cats provides default instances for all the monads in Scala
import cats.instances.option._ // for Monad
Monad[Option].flatMap(Option(1))(a => Option(a*2))

import cats.instances.list._ // for Monad
Monad[List].flatMap(List(1, 2, 3))(a => List(a, a*10))

import cats.instances.vector._ // for Monad
Monad[Vector].flatMap(Vector(1, 2, 3))(a => Vector(a, a*10))


import scala.concurrent.ExecutionContext.Implicits.global //fixes the
// implicit resolution required to summon the instance
import cats.instances.future._ // for Monad
import scala.concurrent._
import scala.concurrent.duration._
val fm = Monad[Future]

//Monad syntax
//Syntax, comes from Functor(for Map), flatMap(for flatMap),
// Applicative(for Pure)
import cats.instances.option._ //Monad
import cats.instances.list._  //Monad
import cats.syntax.applicative._ //Pure
1.pure[Option]
1.pure[List]

//We can define generic functions that performs a calculation on parameters
//that come wrapped in a monad of the user's choice

import cats.Monad
import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap
import scala.language.higherKinds

def _sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  a.flatMap(x => b.map(y => x*x + y*y))

import cats.instances.option._ // for Monad
import cats.instances.list._ // for Monad
sumSquare(Option(3), Option(4))
//Note that sumSquare works for any Monad instance, even one that still
// doesn't exists
sumSquare(List(1, 2, 3), List(4, 5))
// res9: List[Int] = List(17, 26, 20, 29, 25, 34)

//A for-comprehension can do the same as above
def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  for {
    x <- a
    y <- b
  } yield x*x + y*y

implicit val OptionMonad: Monad[Option] = new Monad[Option] {
  override def pure[A](a: A): Option[A] = Some(a)

  override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
    fa match {
      case Some(a) => f(a)
      case None    => None
    }

  override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
    flatMap(fa)(x => pure(f(x)))

  override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] =
    ???
}
val tenDividedSafe: Int => Option[Int] = x => if (x == 0) None else Some(10 / x)
val flatMapped3 = OptionMonad.flatMap(Some(3))(tenDividedSafe)
val flatMapped4 = OptionMonad.flatMap(Some(0))(tenDividedSafe)
