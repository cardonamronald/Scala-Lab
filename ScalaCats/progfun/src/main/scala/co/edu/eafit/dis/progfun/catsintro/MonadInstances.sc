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
