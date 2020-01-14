package co.edu.eafit.dis.progfun.catsintro.ch1.printable

object PrintableSyntax {
  implicit class PrintableSyntaxOps[A](value: A) {
    def format(implicit printable: Printable[A]): String =
      printable.format(value)

    def print(implicit printable: Printable[A]): Unit =
      println(printable.format(value))
  }
}
