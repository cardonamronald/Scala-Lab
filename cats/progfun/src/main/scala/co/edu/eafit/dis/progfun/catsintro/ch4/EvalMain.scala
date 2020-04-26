package co.edu.eafit.dis.progfun.catsintro.ch4
import cats.Eval

object EvalMain extends App {
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    foldRightStackSafe(as, Eval.now(acc))((a, b) => b.map(fn(a, _))).value

  def foldRightStackSafe[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(fn(head, foldRightStackSafe(tail, acc)(fn)))
      case Nil => acc
    }

  println(foldRight((1 to 100000).toList, 1D)(_ + _))
}
