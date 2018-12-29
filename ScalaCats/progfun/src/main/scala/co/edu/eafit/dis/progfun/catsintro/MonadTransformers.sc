import cats.data.OptionT
import cats.instances.list._
import cats.syntax.applicative._ // for pure

type ListOption[A] = OptionT[List, A]

val result1: ListOption[Int] = OptionT(List(Option(10)))

val result2: ListOption[Int] = 32.pure[ListOption]

result1.flatMap { (x: Int) =>
  result2.map { (y: Int) =>
    x + y
  }
}

// Alias Either to a type constructor with one parameter:
type ErrorOr[A] = Either[String, A]
// Build our final monad stack using OptionT:
type ErrorOrOption[A] = OptionT[ErrorOr, A] // Either[String, Option[A]]

import cats.instances.either._ // for Monad
val a = 10.pure[ErrorOrOption]
val b = 32.pure[ErrorOrOption]
val c = a.flatMap(x => b.map(y => x + y))

import scala.concurrent.Future
import cats.data.{EitherT, OptionT}

type FutureEither[A] = EitherT[Future, String, A]
type FutureEitherOption[A] = OptionT[FutureEither, A]

import cats.instances.future._ // for Monad
import scala.concurrent.ExecutionContext.Implicits.global

val futureEitherOr: FutureEitherOption[Int] =
  for {
    a <- 10.pure[FutureEitherOption]
    b <- 32.pure[FutureEitherOption]
  } yield a + b

sealed abstract class HttpError
final case class NotFound(item: String) extends HttpError
final case class BadRequest(msg: String) extends HttpError
type FutureEitherHttp[A] = EitherT[Future, HttpError, A]


import cats.data.Writer
type Logged[A] = Writer[List[String], A]

// Methods generally return untransformed stacks:
def parseNumber(str: String): Logged[Option[Int]] =
  util.Try(str.toInt).toOption match {
    case Some(num) => Writer(List(s"Read $str"), Some(num))
    case None => Writer(List(s"Failed on $str"), None)
  }

// Consumers use monad transformers locally to simplify composition:
def addAll(a: String, b: String, c: String): Logged[Option[Int]] = {
  import cats.data.OptionT
  val result = for {
    a <- OptionT(parseNumber(a))
    b <- OptionT(parseNumber(b))
    c <- OptionT(parseNumber(c))
  } yield a + b + c
  result.value
}

// This approach doesn't force OptionT on other users' code:
val resultA = addAll("1", "2", "3")
val resultB = addAll("1", "a", "3")

//Exercise
import scala.concurrent.Future
import cats.data.EitherT

type Response[A] = EitherT[Future, String, A]

val powerLevels = Map(
  "Jazz" -> 6,
  "Bumblebee" -> 8,
  "Hot Rod" -> 10
)

def getPowerLevel(autobot: String): Response[Int] = {
  powerLevels.get(autobot) match {
    case Some(power) => EitherT.right(Future(power))
    case None => EitherT.left(Future(s"Can't find $autobot, unreacheable"))
  }
}

def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = {
  (for {
    a <- powerLevels.get(ally1)
    b <- powerLevels.get(ally2)
  } yield (a + b) > 15) match {
    case Some(true) => EitherT.right(Future(true))
    case Some(false) => EitherT.right(Future(false))
    case None => EitherT.left(Future(s"$ally2 unreachable"))
  }
}

import scala.concurrent.Await
import scala.concurrent.duration._

def tacticalReport(ally1: String, ally2: String): String = {
  val stack = canSpecialMove(ally1, ally2).value

  Await.result(stack, 1.second) match {
    case Left(value) => s"Comms error: $value"
    case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
    case Right(false) => s"$ally1 and $ally2 need a recharge."
  }
}

tacticalReport("Jazz", "Bumblebee")
// res28: String = Jazz and Bumblebee need a recharge.
tacticalReport("Bumblebee", "Hot Rod")
// res29: String = Bumblebee and Hot Rod are ready to roll out!
tacticalReport("Jazz", "Ironhide")
// res30: String = Comms error: Ironhide unreachable