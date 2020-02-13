package co.edu.eafit.dis.progfun.catsintro.ch4

trait Monad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(a => pure(func(a)))

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(func andThen pure)
}

object Monad {

//Map function on Monad 
//Pure -> Constructor

//Monads in Cats
  import cats.Monad
  import cats.instances.list._
  import cats.instances.option._ // for Monad

  val opt1 = Monad[Option].pure(3)

  val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))

  val opt3 = Monad[Option].map(opt2)(a => 100 * a)

  val list1 = Monad[List].pure(3)

  val list2 = Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))

  val list3 = Monad[List].map(list2)(a => a + 123)

//cats provides default instances for all the monads in Scala Standard Library
  import cats.instances.option._ // for Monad
  Monad[Option].flatMap(Option(1))(a => Option(a * 2))

  import cats.instances.list._ // for Monad
  Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))

  import cats.instances.vector._ // for Monad
  Monad[Vector].flatMap(Vector(1, 2, 3))(a => Vector(a, a * 10))

  import scala.concurrent.ExecutionContext.Implicits.global //fixes the
// implicit resolution required to summon the instance
  import cats.instances.future._
  val fm = Monad[Future]

//Monad syntax
//Syntax, comes from Functor(for Map), flatMap(for flatMap),
// Applicative(for Pure)
  import cats.instances.list._
  import cats.instances.option._
  import cats.syntax.applicative._ //Pure
  1.pure[Option]
  1.pure[List]

//We can define generic functions that performs a calculation on parameters
//that come wrapped in a monad of the user's choice

  import cats.Monad
  import cats.syntax.flatMap._
  import cats.syntax.functor._

  import scala.language.higherKinds

  def _sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
      a.flatMap(x => b.map(y => x * x + y * y))

  import cats.instances.list._
  import cats.instances.option._ // for Monad
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
      } yield x * x + y * y

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
  val flatMapped3                        = OptionMonad.flatMap(Some(3))(tenDividedSafe)
  val flatMapped4                        = OptionMonad.flatMap(Some(0))(tenDividedSafe)
}