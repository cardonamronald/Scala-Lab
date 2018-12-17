import cats.Functor

//Binary Tree Functor
sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

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

TreeFunctor.map(Tree.branch(Tree.leaf(10),
  Tree.branch(Tree.leaf(20),
    Tree.leaf(30))))(_ + 1)