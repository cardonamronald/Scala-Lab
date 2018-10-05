//Scala with cats chapter's 1 exercise

//Printable is a type class
trait Printable[A] {
  def format(value: A): String
}

final case class Person(name: String, email: String)
final case class Cat(name: String, age: Int, color: String)

object PrintableInstances {
  //Type class instance -- Concrete implementations of Printable
  implicit val printableString: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        value
    }

  //Type class instance
  implicit val printableInt: Printable[Int] =
    new Printable[Int] {
      def format(value: Int): String =
        value.toString
    }

  //Another type class instance
  implicit val printableCat: Printable[Cat] =
    new Printable[Cat] {
      override def format(value: Cat): String =
        s"${value.name} is a ${value.age} year old ${value.color} cat"
    }

    //We can compose type classes based on other ones
    //If we had a printable of A we can compose a printable of Option[A]
    implicit def printableOption[A]
        (implicit printable: Printable[A]): Printable[Option[A]] =
      new Printable[Option[A]] {
        override def format(option: Option[A]): String =
          option match {
            case Some(value) => printable.format(value)
            case None => ""
          }
      }
}

//Singleton object with the interface methods
object Printable {
  //Interfaces are methods that accept instances of a type class as
  //implicit parameters

  //Create an interface by placing methods in a singleton object
  def format[A](value: A)(implicit printable: Printable[A]): String =
    printable.format(value)

  def print[A](value: A)(implicit printable: Printable[A]): Unit =
    println(format(value))
}

import PrintableInstances._
//Now, PrintableInstances.printableCat is in the scope,
// and is passed as an implicit parameter
Printable.format(Cat("Ronald", 10, "Red"))
Printable.format(Option("Holi"))



//Interface Syntax,
object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    //These are extension methods, extends existing types with interface methods
    def format(implicit p: Printable[A]): String =
      p.format(value)

    def print(implicit p: Printable[A]): Unit =
      println(p.format(value))
  }
}

import PrintableSyntax._
//Now the syntax is in the scope
Cat("Ronald", 10, "Red").print
Option("Ronald").print

//Summon any value from implicit scope
implicitly[Printable[Cat]]