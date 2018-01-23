package calculator

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    for {
      expr <- namedExpressions
      references = getRefs(expr._2)
      if !(references.contains(me) || references.foreach(getRefs(_).contains(me)))
    } yield (expr._1, eval(expr._2, references))

    def getRefs(expr: Expr) = expr match {
      case Ref(name) => (name, namedExpressions.get(name)) :: getRefs(namedExpressions.get(name))
      case _(a, b) => getRefs(a) :: getRefs(b)
      case Literal(v) => _
    }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = expr match {
    case Divide(a, b) => eval(a, references) / eval(b, references)
    case Times(a, b) => eval(a, references) * eval(b, references)
    case Minus(a, b) =>  eval(a, references) - eval(b, references)
    case Plus(a, b) => eval(a, references) + eval(b, references)
    case Ref(name) => eval(getReferenceExpr(name, references), references)
    case Literal(value) => value
  }

  /** Get the Expr for a referenced variables.
    *  If the variable is not known, returns a literal NaN.
    */
  private def getReferenceExpr(name: String,
                               references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}