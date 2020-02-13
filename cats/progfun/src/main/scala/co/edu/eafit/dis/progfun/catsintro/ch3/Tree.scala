package co.edu.eafit.dis.progfun.catsintro.ch3
import cats.Functor

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

object Tree {
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
  def leaf[A](value: A): Tree[A] = Leaf(value)
}

object TreeInstances {
  implicit def treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
      case Leaf(value) => Leaf(f(value))
    }
  }

  implicit class FunctorOps[F[_] : Functor, A](src: F[A]) {
    def map[B](func: A => B): F[B] = implicitly[Functor[F]].map(src)(func)
  }
}

object Main extends App {

  import TreeInstances._

  val tree = Tree.branch(Tree.leaf(10), Tree.leaf(20))

  println((tree, tree.map(_ + 1)))
}
