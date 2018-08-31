//Implicit val
implicit val Ten: Int = 10

//Implicit parameter
def sum_ten(x: Int)(implicit ten: Int): Int = x + ten
sum_ten(3)

//Caso de uso
//Sumar
def sum[T](l: List[Int]): Int = {
  l.foldLeft(0)(_ + _)
}
sum(List(1, 2, 3, 4, 5, 6, 7, 8, 9))

//Polimorfismo dinamico
def sum[T](l: List[T])(implicit Nt: Numeric[T]): T = {
  import Nt._
  l.foldLeft(zero)(_+_)
}
sum(List(1.22, 4.6, 0))

def get_zero[T](implicit Nt: Numeric[T]): T = Nt.zero

implicit def str2int(s: String): Int = s.toInt
val three: Int = "3"
println(three)

//Implicit class
implicit class StringOps(s: String) {
  def toIntOpt: Option[Int] = try(s.toShort).toOption
}