package co.edu.eafit.dis.progfun.catsintro.ch1

object TypeClasses extends App {
  // Import Show type class and its
  // Interface methods

  import cats.Show
  import cats.instances.int._
  import cats.instances.string._

  val showInt: Show[Int]       = Show.apply[Int]
  val showString: Show[String] = Show.apply[String]

  val intAsString: String =
    showInt.show(123)

  val stringAsString: String =
    showString.show("abc")

  // Import the interface syntax
  // Adds an extension method show to any type
  // For which we have instance of Show in scope

  import cats.syntax.show._

  "abc".show
  1.show
  val a: (String, Int) = ("Pedro", 19)

  case class Cat(name: String, age: Int, color: String)

  // Defining custom instances of cats's type classes
  // We can define an instance of Show simply by implementing the trait for a given
  // type:

  import java.util.Date

  implicit val dateShow: Show[Date] =
    new Show[Date] {
      def show(date: Date): String =
        s"${date.getTime}ms since the epoch."
    }

  // Cat Show
  implicit val catShow: Show[Cat] =
    new Show[Cat] {
      def show(value: Cat): String =
        s"${value.name} is a ${value.age} year old ${value.color} cat"
    }

  // Example: type safe equality
  // List(1, 2, 3).map(Option(_)).filter(item => item == 1) // Here the predicate of the filter always fails(dont even compile)
  // Because its comparing Int and Option[Int]

  import cats.Eq
  import cats.instances.int._
  import cats.instances.option._ //And Option[T]

  val eqInt = Eq[Int]
  eqInt.eqv(1234, 1234)

  // Importing the interface syntax we can do:

  import cats.syntax.eq._

  1234 === 1234

  // These lines are the same, Liskov substitution principle
  (Some(1): Option[Int]) === (None: Option[Int])
  Option(1) === Option.empty[Int]

  // Or using cats.syntax

  import cats.syntax.option._

  1.some === none[Int]
  1.some =!= none[Int]

  // Define our own instances of Eq using the Eq instance method

  import java.util.Date

  import cats.instances.long._ // for Eq

  implicit val dateEq: Eq[Date] = // My own instance of Eq
    Eq.instance[Date] { (date1, date2) =>
      date1.getTime === date2.getTime
    }

  val x = new Date() // now
  val y = new Date() // a bit later than now
  x === x
  x === y

  // Eq for Cat

  import cats.instances.int._
  import cats.instances.string._

  implicit val catEq: Eq[Cat] =
    Eq.instance[Cat] { (cat1: Cat, cat2: Cat) =>
      cat1.name === cat2.name &&
      cat1.age === cat2.age &&
      cat1.color === cat2.color
    }

  val cat1       = Cat("Garfield", 38, "orange and black")
  val cat2       = Cat("Heathcliff", 33, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  cat1 === cat2
  cat1 =!= cat2
  optionCat1 === optionCat2
  optionCat1 =!= optionCat2

  trait F[+A] // the "+" means "covariant"

  sealed trait Shape

  case class Circle(radius: Double) extends Shape

  val circles: List[Circle] = List(Circle(1), Circle(2), Circle(3), Circle(scala.math.Pi))
  val shapes: List[Shape]   = circles
}
