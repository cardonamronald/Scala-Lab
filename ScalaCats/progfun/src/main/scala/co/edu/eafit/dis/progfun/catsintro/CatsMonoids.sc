//Monoids in Cats

//The type classes
import cats.Monoid
import cats.Semigroup


//Instances
import cats.Monoid
import cats.instances.string._  // for Monoid
Monoid[String].combine("Hey", "there")

import cats.Monoid
import cats.instances.int._ // for Monoid
Monoid[Int].combine(32, 10)

import cats.Monoid
import cats.instances.int._ // for Monoid
import cats.instances.option._ // for Monoid

val a = Option(22)
val b = Option(20)

Monoid[Option[Int]].combine(a, b)


//Monoid syntax
import cats.instances.string._
import cats.syntax.semigroup._

val strRes: String =
  "Hi" |+| "There" |+| Monoid[String].empty


//Exercise

//Sums only ints
def add(items: List[Int]): Int = items.foldLeft(0)(_ + _)

//Combines(sums) any type which have a monoid in the implicit scope
def add[A](items: List[A])(implicit monoid: Monoid[A]): A =
  items.foldLeft(monoid.empty)(_ |+| _)

add((1 to 100).toList)
add(List(Option(1), Option(2), Option(3)))

//Define a monoid for Orders, so we can add orders
//without having to modify the code
case class Order(totalCost: Double, quantity: Double)

implicit val OrderMonoid: Monoid[Order] = new Monoid[Order] {
  override def combine(a: Order, b: Order): Order =
    Order(a.totalCost + b.totalCost, a.quantity + b.quantity)
  override def empty: Order = Order(0, 0)
}

val orders = List(Order(1, 2), Order(1, 2), Order(1, 2), Order(1, 2), Order(1, 2), Order(1, 2), Order(1, 2))
add(orders)