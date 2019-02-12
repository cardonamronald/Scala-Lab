import cats.Monoid

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
/**
  * MapReduce Study Case from Scala with
  * cats book by Noel Welsh and Dave Gurnell
  */
//Here is a single-thread implementation for foldMap
def foldMap[A, B: Monoid](values: Vector[A])(f: A => B): B = {
  val vectorOfB: Vector[B] = values.map(f)
  vectorOfB.foldLeft(Monoid[B].empty)(Monoid[B].combine)
}

import cats.instances.int._ // for Monoid
foldMap(Vector(1, 2, 3))(identity)
// res2: Int = 6
import cats.instances.string._ // for Monoid
// Mapping to a String uses the concatenation monoid:
foldMap(Vector(1, 2, 3))(_.toString + "! ")

foldMap("Hello world!".toVector)(_.toString.toUpperCase)


//This alternative def does all to work in one step
import cats.syntax.semigroup._
def foldMap_b[A, B : Monoid](as: Vector[A])(func: A => B): B =
  as.foldLeft(Monoid[B].empty)(_ |+| func(_))

/**
  * Now we are going to implement parallelFoldMap using Futures,
  * Monad for the map phase and Monoid for the reduce phase
  */
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

def parallelFoldMap[A, B: Monoid] (values: Vector[A])
                                  (func: A => B): Future[B] = {
  val batches = if(values.length > Runtime.getRuntime.availableProcessors()) {
    values.grouped(
      Math.floorDiv(values.length, Runtime.getRuntime.availableProcessors()))
  } else {
    Vector(values)
  }

  val mapReduce = for {
    batch <- batches
  } yield Future(foldMap_b(batch)(func))

  //Main reduce
  for {
    iterable <- Future.sequence(mapReduce)
  } yield iterable.foldLeft(Monoid[B].empty)(Monoid[B].combine)
}


  Await.result(
    parallelFoldMap(List(1, 2, 3, 4, 5, 6, 7, 8, 9).toVector)(_.toString), 1.second)