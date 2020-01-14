package co.edu.eafit.dis.progfun.catsintro.ch1

import co.edu.eafit.dis.progfun.catsintro.ch1.jsonwriter.JsonWriter

case class Person(name: String, email: String)

object Person {
  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }
}