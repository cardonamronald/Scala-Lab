import cats.Monad
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