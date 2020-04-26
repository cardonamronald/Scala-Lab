package co.edu.eafit.dis.progfun.catsintro.ch4

import cats.data.Writer
import cats.syntax.applicative._ // for pure
import cats.instances.vector._ // for Monoid
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


object WriterMain extends App {

  type Logged[A] = Writer[Vector[String], A]

  def slowly[A](body: => A): A =
    try body finally Thread.sleep(200)


  // Not the most fp-hipster solution, but it's mine :)

  def factorial(n: Int): Logged[Int] = {
    factorialLogged(n.pure[Logged])
  }

  def factorialLogged(logged: Logged[Int]): Logged[Int] = {
    logged.flatMap(n => slowly {
      if (n == 0) 1.pure[Logged].mapWritten(_.appended(s"fact $n 1"))
      else factorialLogged((n - 1).pure[Logged])
        .flatMap(log => (n * log).pure[Logged])
        .mapBoth((v, ans) => (v.appended(s"fact $n $ans"), ans))
    })
  }

  val Vector((logA, ansA), (logB, ansB)) =
    Await.result(Future.sequence(Vector(
      Future(factorial(3).run),
      Future(factorial(5).run)
    )), 5.seconds)

  println(ansA)
  println(logA)
  println(ansB)
  println(logB)
}
