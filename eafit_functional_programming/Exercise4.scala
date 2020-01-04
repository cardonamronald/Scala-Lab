package co.edu.eafit.dis.progfun.scala.intro

object Exercise4 extends App {
  def sumInts(a: Int, b: Int): Int = if (a > b) 0 else a + sumInts(a + 1, b)

  def sumCubes(a: Int, b: Int): Int = if (a > b) 0 else cube(a) + sumCubes(a + 1, b)

  def sumFactorials(a: Int, b: Int): Int = if (a > b) 0 else factorial(a) + sumFactorials(a + 1, b)

  // Generalize
  def sum(f: Int => Int, a: Int, b: Int): Int = if (a > b) 0 else f(a) + sum(f, a + 1, b)

  def sumIntegers(a: Int, b: Int): Int = sum(id, a, b)
  def sumFactorialss(a: Int, b: Int): Int = sum(factorial, a, b)
  def sumCubess(a: Int, b: Int): Int = sum(cube, a, b)

  // Where
  def id(x: Int): Int = x
  def cube(x: Int): Int = x * x * x
  def factorial(x: Int): Int = if (x <= 1) 1 else x * factorial(x - 1)

  // Anonymous functions
  //(x: Int) => x * x * x // Cube
  //(x: Int) => x // Identity

  // Exercise
  def tailRecsum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }

    loop(a, 0)
  }


  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Var(name: String) extends Expr
}