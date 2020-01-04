package co.edu.eafit.dis.progfun.scala.intro

object Exercise6 extends App {
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Var(name: String) extends Expr

  def eval(e: Expr): Int = e match {
    case Number(n) => n
    case Sum(e1, e2) => eval(e1) + eval(e2)
  }

  def show(e: Expr): String = e match {
    case Number(n) => n.toString
    case Sum(e1, e2) =>
      show(e1).concat(" + ").concat(show(e2))
  }

  eval(Sum(Number(4), Number(10)))

  show(Sum(Number(4), Number(10)))
}