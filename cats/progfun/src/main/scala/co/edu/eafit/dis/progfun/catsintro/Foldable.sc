List(1, 2, 3)
  .foldLeft(List.empty[Int])((tail, head) => head :: tail)

List(1, 2, 3)
  .foldRight(List.empty[Int])((head, tail) => head :: tail)

val ints = List(1, 2, 3)

def map[A, B](xs: List[A])(f: A => B): List[B] = {
  xs.foldRight(List.empty[B])((head, tail) => f(head) :: tail)
}

flatMap(List(1, 2, 3))(a => List(a, a * 10, a * 100))

def flatMap[A, B](xs: List[A])(f: A => List[B]): List[B] = {
  xs.foldRight(List.empty[B])((head, tail) => f(head) ::: tail)
}

filter(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))(_ % 2 == 0)

//For sum, well need a monoid for combine and zero element

import cats.Monoid

def filter[A](xs: List[A])(p: A => Boolean): List[A] = {
  xs.foldRight(List.empty[A])((head, tail) => if (p(head)) head :: tail else tail)
}

import cats.instances.int._

sum(List(1, 2, 3, 4, 5))

//Foldable in cats

import cats.Foldable
import cats.instances.list._ // for Foldable

def sum[A](xs: List[A])(implicit monoid: Monoid[A]): A = {
  xs.foldRight(monoid.empty)(monoid.combine)
}
Foldable[List].foldLeft(ints, 0)(_ + _)