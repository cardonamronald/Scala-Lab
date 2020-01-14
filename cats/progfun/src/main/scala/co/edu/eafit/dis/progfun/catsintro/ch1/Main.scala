package co.edu.eafit.dis.progfun.catsintro.ch1

import co.edu.eafit.dis.progfun.catsintro.ch1.printable.Printable

object Main extends App {
  Json.toJson(Person("Ronald", "ronald@gmail.com"))

  import co.edu.eafit.dis.progfun.catsintro.ch1.jsonwriter.JsonSyntax._
  Person("Ronald", "ronald@gmail.com").toJson

  Json.toJson(Option("A string"))

  Printable.print(Cat("Nacho", 12, "black"))

  import co.edu.eafit.dis.progfun.catsintro.ch1.printable.PrintableSyntax._
  Cat("Nacho", 12, "black").print
}