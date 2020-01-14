package co.edu.eafit.dis.progfun.catsintro.ch1.jsonwriter

import co.edu.eafit.dis.progfun.catsintro.ch1.{JsNull, JsString, Json}

// The "serialize to JSON" behaviour is encoded in this trait
trait JsonWriter[A] {
  def write(value: A): Json
}

object JsonWriter {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }

  implicit def optionWriter[A : JsonWriter]: JsonWriter[Option[A]] = {
    new JsonWriter[Option[A]] {
      def write(value: Option[A]): Json = value match {
        case Some(value) => implicitly[JsonWriter[A]].write(value)
        case None => JsNull
      }
    }
  }
}