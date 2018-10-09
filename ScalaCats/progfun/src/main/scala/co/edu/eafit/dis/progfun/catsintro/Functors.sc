// The Functor type class
import scala.language.higherKinds
import cats.Functor
import cats.instances.list._
import cats.instances.option._

//Usage:
val list1 = List(1, 2, 3)
val list2 = Functor[List].map(list1)(_ * 2)
val option1 = Option(123)
val option2 = Functor[Option].map(option1)(_.toString)

val mapped1 = List(1, 2, 3)
    .map(x => x + 1)
    .map(x => x * 2)
    .map(x => s"${x}!")

//This is the same as doing the following
val mapped2 = Functor[List].map(List(1, 2, 3))(x => x + 1).map(x => x * 2).map(x => s"${x}!")

mapped1 == mapped2 //res0: Boolean = true

//So, why use Functors if scala's basic language features allows you to do the same?

// We could also use the Functors 'lift' method to transform a function A => B into a function F[A] => F[B]
val func = (x: Int) => x + 1 // Int => Int
val liftedFunc = Functor[Option].lift(func) //Option[A] => Option[B]
  liftedFunc(Option(1))

//The Syntax -- The map method
import cats.instances.function._  // for Functor
import cats.syntax.functor._ // for map

val func1 = (a: Int) => a + 1
val func2 = (a: Int) => a * 2
val func3 = (a: Int) => a + "!"
val func4 = func1.map(func2).map(func3)
func4(123)

//Higher level of abstraction
def doMath[F[_]](start: F[Int])(implicit functor: Functor[F]): F[Int] =
  start.map(n => n + 1 * 2)

import cats.instances.option._ // for Functor
import cats.instances.list._ // for Functor

doMath(Option(20))
doMath(List(1, 2, 3))

//Build a custom Functor for a custom type only by defining its map method

//Option functor
implicit val optionFunctor: Functor[Option] =
  new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }