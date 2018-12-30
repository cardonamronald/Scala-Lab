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

(Option("Garfield"),
  Option(1978),
  Option("Orange & Black")
).mapN(Cat.apply)


/**
  * Combine Monoids using invariant
  */
import cats.Monoid
import cats.instances.boolean._ // for Monoid
import cats.instances.int._ // for Monoid
import cats.instances.list._ // for Monoid
import cats.instances.string._ // for Monoid
import cats.syntax.apply._ // for imapN
import cats.instances.invariant._

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
).imapN(Cat2.apply)(c => Cat2.unapply(c).get)

import cats.syntax.semigroup._ // for |+|
val garfield: Cat2 = Cat2("Garfield", 1978, List("Lasagne"))
val heathcliff: Cat2 = Cat2("Heathcliff", 1988, List("Junk Food"))

//garfield |+| heathcliff
// If has two monoids in scope it will fail
// res17: Cat = Cat(GarfieldHeathcliff,3966,List(Lasagne, Junk Food))

import cats.instances.either._ // for Semigroupal

type ErrorOr[A] = Either[Vector[String], A]

Semigroupal[ErrorOr].product(
  Left(Vector("Error 1")),
  Left(Vector("Error 2"))
)
// res7: ErrorOr[(Nothing, Nothing)] = Left(Vector(Error 1))

//Exercise
import scala.language.higherKinds
import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._

def product[M[_]: Monad, A, B](x: M[A], y: M[B]): M[(A, B)] =
  x.flatMap(a => y.map(b => (a, b)))

/**
  * The validated data type
  * */
import cats.Semigroupal
import cats.data.Validated
import cats.instances.list._ // for Monoid

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
  * Exercise: Form Validation*/
case class User(name: String, age: Int)

def readName(map: Map[String, String]): Either[List[String], String] = ???

def readAge(map: Map[String, String]): Either[List[String], String] = ???

def getValue(field: String): String =  ???

def parseInt(number: String): Int = Integer.parseInt(number)

def nonBlank(string: String) = ???

def NonNegative(int: Int) = ???














