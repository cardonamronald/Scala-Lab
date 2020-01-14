package co.edu.eafit.dis.progfun.catsintro.ch1.jsonwriter

import co.edu.eafit.dis.progfun.catsintro.ch1.Json

object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit jsonWriter: JsonWriter[A]): Json =
      jsonWriter.write(value)
  }
}
