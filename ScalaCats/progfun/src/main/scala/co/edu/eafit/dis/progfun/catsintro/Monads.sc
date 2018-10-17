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
//Flat map calculate permutations and combinations over that lists
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
trait Monad[F[_]] {
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
// implicit resolu􀦞on required to summon the instance
import cats.instances.future._ // for Monad
import scala.concurrent._
import scala.concurrent.duration._
val fm = Monad[Future]

//Syntax, comes from Functor(Map), flatMap(flatMap), Applicative(Pure)
import cats.Monad
import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap
import scala.language.higherKinds

def _sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  a.flatMap(x => b.map(y => x * x + y * y))

import cats.instances.option._ // for Monad
import cats.instances.list._ // for Monad
sumSquare(Option(3), Option(4))
// res8: Option[Int] = Some(25)
sumSquare(List(1, 2, 3), List(4, 5))
// res9: List[Int] = List(17, 26, 20, 29, 25, 34)



def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  for {
    x <- a
    y <- b
  } yield x*x + y*y