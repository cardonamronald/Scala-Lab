import scala.math.BigDecimal

val a = List(0.8676, 0.7898, 0.9989e1, 0.8788e1, 0.7999e2, 0.6266e2, 0.7889e3, 0.7789e3, 0.8999e4, 0.9787e4)
val sortedasc = a.sortWith(_ > _)
sortedasc.sum
val sorteddesc = a.sortWith(_<_)
sorteddesc.sum

def sum (xs: List[Int]): Int = {
  xs match {
    case x :: tail => x + sum(tail)
    case Nil => 0
  }
}


sorteddesc match {
  case x :: Nil => BigDecimal(x).setScale(4, BigDecimal.RoundingMode.HALF_UP)
  case x :: xs => BigDecimal(x + xs.head).setScale(4, BigDecimal.RoundingMode.HALF_UP)
  case Nil =>
}