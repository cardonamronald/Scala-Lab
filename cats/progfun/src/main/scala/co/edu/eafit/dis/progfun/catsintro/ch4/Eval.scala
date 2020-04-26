package co.edu.eafit.dis.progfun.catsintro.ch4

object Eval extends App {
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail =>
        fn(head, foldRightEval(tail, cats.Eval.now(acc))()
      case Nil => acc
    }

  def foldRightEval[A, B](as: List[A], acc: cats.Eval[B])(fn: (A, cats.Eval[B]) => cats.Eval[B]): cats.Eval[B] =
    as match {
      case head :: tail => cats.Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
      case Nil => acc
    }
}
