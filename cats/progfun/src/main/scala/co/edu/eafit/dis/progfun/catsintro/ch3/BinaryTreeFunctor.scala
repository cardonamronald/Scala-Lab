object BinaryTreeFunctor extends App {
  import cats.{Functor, Monad}

//Binary Tree Functor
  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A)                        extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
      Branch(left, right)
    def leaf[A](value: A): Tree[A] =
      Leaf(value)
  }

  /** To implement a Functor we only need to implement its map function
    * and tag it 'implicit'
    */
  implicit val TreeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Leaf(v) => Leaf(f(v))
      case Branch(left: Tree[A], right: Tree[A]) =>
        Branch(map(left)(f), map(right)(f))
    }
  }

  TreeFunctor.map(Tree.branch(Tree.leaf(10), Tree.branch(Tree.leaf(20), Tree.leaf(30))))(_ + 1)

  /**
    * Binary Tree Monad
    * */
  implicit val TreeMonad: Monad[Tree] = new Monad[Tree] {
    override def pure[A](x: A): Tree[A] = Leaf(x)

    //Saca del contexto y aplica la funcion
    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] =
      fa match {
        case Branch(l, r) => Branch(flatMap(l)(f), flatMap(r)(f))
        case Leaf(v)      => f(v)
      }

    /**
      * Not tail-rec */
    def tailRecM[A, B](arg: A)(func: A => Tree[Either[A, B]]): Tree[B] =
      func(arg) match {
        case Branch(l, r) =>
          Branch(
            flatMap(l) {
              case Left(l)  => tailRecM(l)(func)
              case Right(l) => pure(l)
            },
            flatMap(r) {
              case Left(r)  => tailRecM(r)(func)
              case Right(r) => pure(r)
            }
          )
        case Leaf(Left(value))  => tailRecM(value)(func)
        case Leaf(Right(value)) => Leaf(value)
      }
  }
}
