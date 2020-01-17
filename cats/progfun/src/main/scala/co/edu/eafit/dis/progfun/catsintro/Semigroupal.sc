/**
    * Semigroupal is a type class that allows us to combine contexts
    * without worrying about the order of the context's execution
    */
import cats.Semigroupal
import cats.instances.option._ // for Semigroupal

Semigroupal[Option].product(Some(123), Some("abc"))
// res0: Option[(Int, String)] = Some((123,abc))
Semigroupal[Option].product(None, Some("abc"))
// res1: Option[(Nothing, String)] = None
Semigroupal[Option].product(Some(123), None)
// res2: Option[(Int, Nothing)] = None
Semigroupal.tuple3(Option(1), Option(2), Option(3))
// res3: Option[(Int, Int, Int)] = Some((1,2,3))
Semigroupal.tuple3(Option(1), Option(2), Option.empty[Int])
// res4: Option[(Int, Int, Int)] = None
Semigroupal.map3(Option(1), Option(2), Option(3))(_ + _ + _)
// res5: Option[Int] = Some(6)

//Syntax
import cats.syntax.apply._

(Option(123), Option("abc")).tupled
// res7: Option[(Int, String)] = Some((123,abc))

case class Cat(name: String, born: Int, color: String)

(Option("Garfield"), Option(1978), Option("Orange & Black")).mapN(Cat.apply)

/**
    * Combine Monoids using invariant
    */
import cats.Monoid
import cats.instances.int._
import cats.instances.invariant._
import cats.instances.list._
import cats.instances.string._
import cats.syntax.apply._

case class Cat2(name: String, yearOfBirth: Int, favoriteFoods: List[String])

val tupleToCat: (String, Int, List[String]) => Cat2 = Cat2.apply _

val catToTuple: Cat2 => (String, Int, List[String]) =
    cat => (cat.name, cat.yearOfBirth, cat.favoriteFoods)

implicit val catMonoid: Monoid[Cat2] = (
    Monoid[String],
    Monoid[Int],
    Monoid[List[String]]
  ).imapN(tupleToCat)(catToTuple)

/**
    * Or use the built-in apply and unapply methods that are provided for
    * every case class
    * https://stackoverflow.com/questions/48967836/could-not-find-implicit-value-for-parameter-semigroupal
    * */
  implicit val catMonoid2: Monoid[Cat2] = (
    Monoid[String],
    Monoid[Int],
    Monoid[List[String]]
  ).imapN(Cat2.apply)(c => Cat2.unapply(c).get) // for |+|
  val garfield: Cat2   = Cat2("Garfield", 1978, List("Lasagne"))
val heathcliff: Cat2 = Cat2("Heathcliff", 1988, List("Junk Food"))

//garfield |+| heathcliff
// If has two monoids in scope it will fail
// res17: Cat = Cat(GarfieldHeathcliff,3966,List(Lasagne, Junk Food)) // for Semigroupal

type ErrorOr[A] = Either[Vector[String], A]

Semigroupal[ErrorOr].product(
    Left(Vector("Error 1")),
    Left(Vector("Error 2"))
  )
// res7: ErrorOr[(Nothing, Nothing)] = Left(Vector(Error 1))

//Exercise
import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._

import scala.language.higherKinds

def product[M[_]: Monad, A, B](x: M[A], y: M[B]): M[(A, B)] =
    x.flatMap(a => y.map(b => (a, b)))

/**
    * The validated data type
    * */
import cats.Semigroupal
import cats.data.Validated // for Monoid

type AllErrorsOr[A] = Validated[List[String], A]

Semigroupal[AllErrorsOr].product(
    Validated.invalid(List("Error 1")),
    Validated.invalid(List("Error 2"))
  )

import cats.syntax.validated._ //For valid and invalid

123.valid.map(_ * 100)
// res17: cats.data.Validated[Nothing,Int] = Valid(12300)
"?".invalid.leftMap(_.toString)
// res18: cats.data.Validated[String,Nothing] = Invalid(?)
123.valid[String].bimap(_ + "!", _ * 100)
// res19: cats.data.Validated[String,Int] = Valid(12300)
"?".invalid[Int].bimap(_ + "!", _ * 100)
// res20: cats.data.Validated[String,Int] = Invalid(?!)

/**
    * Exercise: Form Validation
    * */
import cats.data.Validated
import cats.syntax.either._ // for catchOnly

type FormData    = Map[String, String]
type FailFast[A] = Either[List[String], A]
type FailSlow[A] = Validated[List[String], A]
type NumFmtExn   = NumberFormatException

case class User(name: String, age: Int)

def readName(data: FormData): FailFast[String] =
    getValue("name")(data).flatMap(nonBlank("name"))

def readAge(data: FormData): FailFast[Int] =
    getValue("age")(data)
      .flatMap(nonBlank("age"))
      .flatMap(parseInt("age"))
      .flatMap(nonNegative("age"))

def getValue(name: String)(data: FormData): FailFast[String] =
    data.get(name).toRight(List(s"$name field not specified"))

def parseInt(name: String)(data: String): FailFast[Int] =
    Either.catchOnly[NumFmtExn](data.toInt).leftMap(_ => List(s"$name must be an integer"))

def nonBlank(name: String)(data: String): FailFast[String] =
    Either.right(data).filterOrElse(_.nonEmpty, List(s"$name cannot be blank"))

def nonNegative(name: String)(data: Int): FailFast[Int] =
    Either.right(data).filterOrElse(_ >= 0, List(s"$name must be non-negative"))

import cats.instances.list._
import cats.syntax.apply._   // for mapN

def readUser(data: FormData): FailSlow[User] =
    (readName(data).toValidated, readAge(data).toValidated).mapN(User.apply)

readUser(Map("name" -> "Dave", "age" -> "37"))
// res48: FailSlow[User] = Valid(User(Dave,37))
readUser(Map("age" -> "-1"))
// res49: FailSlow[User] = Invalid(List(name field not specified, age must be non-negative))












