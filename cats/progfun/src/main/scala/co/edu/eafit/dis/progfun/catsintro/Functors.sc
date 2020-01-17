// The Functor type class
import cats.Functor
import cats.instances.list._
import cats.instances.option._

import scala.language.higherKinds

//Usage:
  val list1   = List(1, 2, 3)
val list2   = Functor[List].map(list1)(_ * 2)
val option1 = Option(123)
val option2 = Functor[Option].map(option1)(_.toString)

val mapped1 = List(1, 2, 3)
    .map(x => x + 1)
    .map(x => x * 2)
    .map(x => s"${x}!")

//This is the same as doing the following
  val mapped2 = Functor[List].map(List(1, 2, 3))(x => x + 1).map(x => x * 2).map(x => s"${x}!")

mapped1 == mapped2 //res0: Boolean = true

//So, why use Functors if scala's basic language features allows you to do the
// same?

// We could also use the Functors 'lift' method to transform a function A => B
// into a function F[A] => F[B]
  val func       = (x: Int) => x + 1 // Int => Int
  val liftedFunc = Functor[Option].lift(func) //Option[A] => Option[B]
liftedFunc(Option(1))

//The Syntax -- The map method
import cats.instances.function._
import cats.syntax.functor._     // for map

val func1 = (a: Int) => a + 1
val func2 = (a: Int) => a * 2
val func3 = (a: Int) => a + "!"
val func4 = func1.map(func2).map(func3)
func4(123)

//Higher level of abstraction, abtract over Functors of any type
  def doMath[F[_]](start: F[Int])(implicit functor: Functor[F]): F[Int] =
    start.map(n => n + 1 * 2).map(_ - 1).map(_ / 800).map(_ + 9)

import cats.instances.list._
import cats.instances.option._   // for Functor

doMath(Option(20))    //Cool :)
doMath(List(1, 2, 3)) //:o

//Build a custom Functor for a custom type only by defining its map method

//Option functor
  implicit val optionFunctor: Functor[Option] =
    new Functor[Option] {
      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
    }

//Contravariant Functors -- Allows us to prepend operations into a chain with
// contramap

// We have tha ability con convert an A into a string
// We also know how to convert a B into an A
// And we need to convert a B into a string, so..
// We convert B to A and A to string, that's
// (B => A) => String, then:
  trait Printable[A] {
    self =>
    def format(value: A): String
    def contramap[B](func: B => A): Printable[B] =
      new Printable[B] {
        def format(value: B): String =
          self.format(func(value))
      }
  }

implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      override def format(value: String): String = "\"" + value + "\""
    }

implicit val booleanPrintable: Printable[Boolean] =
    new Printable[Boolean] {
      override def format(value: Boolean): String = if (value) "yes" else "no"
    }

final case class Box[A](value: A)

implicit def printableBox[A](implicit printable: Printable[A]): Printable[Box[A]] =
    new Printable[Box[A]] {
      override def format(box: Box[A]): String = printable.format(box.value)
    }
//We know how to transform a Printable[A] into a Printable[B] with contramap
//So if we have a printable of what is inside of the box we easily obtain a
// Printable of the box itself
  implicit def boxPrintable[A](implicit printable: Printable[A]): Printable[Box[A]] =
    printable.contramap[Box[A]](_.value) //box => box.value

//Conclusion: Wherever we have an F[A] and a conversion A => B
// we can always convert to an F[B], because of Covariance

//Invariant Functors -- Prepend and Append with imap method
  trait Codec[A] {
    self =>
    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B => A): Codec[B] =
      new Codec[B] {
        override def encode(value: B): String = self.encode(enc(value))

        override def decode(value: String): B = dec(self.decode(value))
      }
  }

def encode[A](value: A)(implicit c: Codec[A]): String =
    c.encode(value)

def decode[A](value: String)(implicit c: Codec[A]): A =
    c.decode(value)

implicit val stringCodec: Codec[String] =
    new Codec[String] {
      def encode(value: String): String = value
      def decode(value: String): String = value
    }

//We can easily generate Codecs for types any type
  implicit val intCodec: Codec[Int] =
    stringCodec.imap(_.toInt, _.toString)

implicit val booleanCodec: Codec[Boolean] =
    stringCodec.imap(_.toBoolean, _.toString)

implicit val doubleCodec: Codec[Double] =
    stringCodec.imap(_.toDouble, _.toString)

/**implicit def boxCodec[A](implicit codec: Codec[A]): Codec[Box[A]] =
    *new Codec[Box[A]] {
    *override def encode(box: Box[A]): String = codec.encode(box.value)
 **
 *override def decode(value: String): Box[A] = Box(codec.decode(value))
    *}*/
  implicit def codecBox[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap[Box[A]](Box(_), _.value)

encode(123.4)
// res0: String = 123.4
decode[Double]("123.4")
// res1: Double = 123.4
encode(Box(123.4))
// res2: String = 123.4
decode[Box[Double]]("123.4")
// res3: Box[Double] = Box(123.4)

//Contravariant in Cats -- -- --
import cats.{Contravariant, Show}
import cats.instances.string._
val showString = Show[String]
val showSymbol = Contravariant[Show].contramap(showString)((sym: Symbol) => s"'${sym.name}")
showSymbol.show('dave)

//Invariant in Cats -- -- --
// There's an instance of Invariant for Monoids
import cats.Monoid
import cats.instances.string._
import cats.syntax.invariant._
import cats.syntax.semigroup._ // for |+|

implicit val symbolMonoid: Monoid[Symbol] =
    Monoid[String].imap(Symbol.apply)(_.name)

Monoid[Symbol].empty
// res5: Symbol = '
'a |+| 'few |+| 'words
// res6: Symbol = 'afewwords