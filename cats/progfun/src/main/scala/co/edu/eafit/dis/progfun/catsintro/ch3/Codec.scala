package co.edu.eafit.dis.progfun.catsintro.ch3

/**
  * It's just follow the types :)
  * */
trait Codec[A] {
  self =>

  def encode(value: A): String

  def decode(value: String): A

  def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
    override def encode(value: B): String = self.encode(enc(value))
    override def decode(value: String): B = dec(self.decode(value))
  }
}

final case class Box[A](value: A)

object CodecInstances {
  implicit val stringCodec: Codec[String] =
    new Codec[String] {
      def encode(value: String): String = value
      def decode(value: String): String = value
    }

  implicit val intCodec: Codec[Int] =
    stringCodec.imap(_.toInt, _.toString)

  implicit val booleanCodec: Codec[Boolean] =
    stringCodec.imap(_.toBoolean, _.toString)

  implicit val doubleCodec: Codec[Double] =
    stringCodec.imap(_.toDouble, _.toString)

  implicit def boxCodec[A: Codec]: Codec[Box[A]] =
    implicitly[Codec[A]].imap(Box.apply, _.value)
}

object CodecMain extends App {
  import CodecInstances._

  def encode[A: Codec](value: A): String =
    implicitly[Codec[A]].encode(value)

  def decode[A: Codec](value: String): A =
    implicitly[Codec[A]].decode(value)

  println(encode(123.4))
  // res0: String = 123.4

  println(decode[Double]("123.4"))
  // res1: Double = 123.4

  println(encode(Box(123.4)))
  // res2: String = 123.4

  println(decode[Box[Double]]("123.4"))
  // res3: Box[Double] = Box(123.4)
}
