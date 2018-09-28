//Scala with cats chapter's 1 exercise
trait Printable[A] {
  def format(value: A): String
}

final case class Person(name: String, email: String)

object PrintableInstances {
  implicit val printableString: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        value
    }

  implicit val printableInt: Printable[Int] =
    new Printable[Int] {
      def format(value: Int): String =
        value.toString
    }
}

object Printable {
  def format[A](value: A)(implicit printable: Printable[A]): String =
    printable.format(value)

  def print[A](value: A)(implicit printable: Printable[A]): Unit =
    println(format(value))
}

class Cat(name: String, age: Int, color: String)

implicit val catPrintable = new Printable[Cat] {
  def format(cat: Cat) = {
    val name = Printable.format(cat.name)
    val age = Printable.format(cat.age)
    val color = Printable.format(cat.color)
    s"$name is a $age year-old $color cat."
  }
}