import cats.Functor

//Binary Tree Functor
sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

implicit val branchFunctor: Functor[Branch] =
  new Functor[Branch] {
    override def map[A, B](fa: Branch[A])(f: A => B): Branch[B] = map(fa)(f)
  }

implicit val leafFunctor: Functor[Leaf] =
  new Functor[Leaf] {
    override def map[A, B](fa: Leaf[A])(f: A => B): Leaf[B] = map(fa)(f)
  }

implicit val TreeFunctor: Functor[Tree] =
  new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
      case Leaf(v) => Leaf(map(v)(f))
    }
  }