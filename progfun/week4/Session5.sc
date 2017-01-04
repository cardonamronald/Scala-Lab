trait Expr
  case class Number(n : Int) extends Expr
  case class Sum(e1 : Expr, e2 : Expr) extends Expr
  case class Var(x : String) extends Expr
  case class Prod(e1 : Expr, e2 : Expr) extends Expr

object exprs {
  def show(e: Expr): String = e match {
    case Number (n) => n.toString
    case Var (x) => x
    case Sum (e1, e2) => show(e1) + " + " + show(e2)
    case Prod (e1, e2) => show(e1) + " * " + show(e2)
  }
}
