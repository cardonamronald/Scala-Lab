//The identity monad
//We can use pure values in monadic computations using the Id[_] monad.
//So we can use our sum square method to sum options, lists, ... or just ints
import cats.Id
import cats.Monad
import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap
import scala.language.higherKinds

def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  a.flatMap(x => b.map(y => x*x + y*y))
sumSquare(3: Id[Int], 4: Id[Int]) //Puts int into a monadic context using Id

//Exercise: Defining my own Id Monad
type Id[A] = A
implicit val IdMonad: Monad[Id] = new Monad[Id] {
  override def pure[A](a: A): Id[A] = a

  override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)

  override def map[A, B](fa: Id[A])(f: A => B): Id[B] =
    flatMap(fa)(f)

  override def tailRecM[A, B](a: A)(f: (A) => Id[Either[A, B]]): Id[B] = ???
}

//Either
import cats.syntax.either._ //For scalaVersions < 2.12
val either1: Either[String, Int] = Right(10)
val either2: Either[String, Int] = Right(32)
for {
  b <- either1
  a <- either2
} yield a + b

//asRight and asLeft allows us to create instances of option only by defining
//one type parameter
val a = 3.asRight[String] //Either[String, Int]
val b = "Hello".asLeft[Int] //Either[String, Int]

def countPositive(nums: List[Int]) =
  nums.foldLeft(0.asRight[String]){(accum, num) =>
    if(num > 0) {
      accum.map(_ + 1)
    } else {
      Left("Negative. Stopping")
    }
  }

//Sequence computations with flatMap, if one computation fails, the remaining
//Computations are not run
for {
  a <- 1.asRight[String]
  b <- 0.asRight[String]
  c <- if(b == 0) "Div0".asLeft[Int]
  else (a / b).asRight[String]
} yield c * 100

//We can do error handling with Either as follows
type Result[A] = Either[Throwable, A]
//Or more specifically
sealed trait LoginError extends Product with Serializable

final case class UserNotFound(username: String)
  extends LoginError

final case class PasswordIncorrect(username: String)
  extends LoginError

final case class UnexpectedError(username: String) extends LoginError

case class User(username: String, password: String)

type LoginResult = Either[LoginError, User]

def handleError(error: LoginError): Unit =
  error match {
    case UserNotFound(usr) => println(s"User not found : $usr")
    case PasswordIncorrect(usr) => println(s"Incorrect password: $usr")
    case UnexpectedError(usr) => println("UnexpectedError")
  }

val result1: LoginResult = User("user", "pass").asRight
val result2: LoginResult = UserNotFound("usr").asLeft

result1.fold(handleError, println)
result2.fold(handleError, println)

//MonadError Instance
import cats.MonadError
import cats.instances.either._
//F is the type of the monad
//E is the type of the error contained within F
trait MyMonadError[F[_], E] extends Monad[F] {
  def raiseError[A](e: E): F[A]

  def handleError[A](fa: F[A])(f: E => A): F[A]

  def ensure[A](fa: F[A])(e: E)(f: A => Boolean): F[A]
}

type ErrorOr[A] = Either[String, A]
val monadError = MonadError[ErrorOr, String]

//Raising errors
val success = monadError.pure(42)
val failure = monadError.raiseError("Badness")

//EvalMonad
import cats.Eval
val now = Eval.now(math.random() + 1000) // val eager and memoized
val later = Eval.later(math.random() + 2000) // lazy and memoized(lazy val)
val always = Eval.always(math.random() + 3000) // lazy, not memoized

//We can extract the result of any Eval using its value method
now.value
later.value
always.value

def factorial(n: BigInt): Eval[BigInt] =
  if(n == 1) {
    Eval.now(n)
  } else {
    Eval.defer(factorial(n - 1).map(_ * n))
  }

//Safer folding using Eval
def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
  foldRightEval(as, Eval.now(acc)) { (a, b) =>
    b.map(fn(a, _))
  }.value

def foldRightEval[A, B](as: List[A], acc: Eval[B])
                       (fn: (A, Eval[B]) => Eval[B]): Eval[B] =
  as match {
    case head :: tail => Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
    case Nil => acc
  }

foldRight((1 to 100000).toList, 0L)(_ + _)

//The writer Monad





