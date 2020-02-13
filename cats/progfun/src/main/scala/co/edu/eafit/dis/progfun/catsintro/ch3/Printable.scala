package co.edu.eafit.dis.progfun.catsintro.ch3

trait Printable[A] {

  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B] {
      def format(value: B): String =
        Printable.this.format(func(value))
    }
}

final case class Box[A](value: A)

object PrintableInstances {
  implicit val stringPrintable: Printable[String] = new Printable[String] {
      def format(value: String): String = "\"" + value + "\""
    }

  implicit val booleanPrintable: Printable[Boolean] = new Printable[Boolean] {
      def format(value: Boolean): String = if(value) "yes" else "no"
    }

  implicit def boxPrintable[A : Printable]: Printable[Box[A]] =
    implicitly[Printable[A]].contramap(_.value)
}

object MainPrintable extends App {
  import PrintableInstances._

  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  println(format("hello"))

  println(format(true))

  println(format(Box("Hello World !")))

  println(format(Box(true)))

  println(format("I don't even understand what I just did !! "))
}
