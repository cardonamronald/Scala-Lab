package co.edu.eafit.dis.progfun.catsintro.ch1

import co.edu.eafit.dis.progfun.catsintro.ch1.printable.Printable

case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catPrintable: Printable[Cat] =
    new Printable[Cat] {
      override def format(value: Cat): String =
        s"${value.name} is a ${value.age} year old ${value.color} cat"
    }
}