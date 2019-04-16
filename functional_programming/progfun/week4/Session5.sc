trait Expr
  case class Number(n : Int) extends Expr
  case class Sum(e1 : Expr, e2 : Expr) extends Expr
  case class Var(x : String) extends Expr
  case class Prod(e1 : Expr, e2 : Expr) extends Expr


  def show(e: Expr): String = e match {
    case Number(n) => n.toString
    case Var(x) => x
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      val a = e1 match {
        case Sum(x, y) => "(" + show(x) + " + " + show(y) + ")"
        case _ => show(e1)
      }
      val b = e2 match {
        case Sum(x, y) => "(" + show(x) + " + " + show(y) + ")"
        case _ => show(e2)
      }
      a + " * " + b
    }
  }

  show(Sum(Prod(Number(2), Var("x")), Var("y")))
  show(Prod(Sum(Number(2), Var("x")), Var("y")))
