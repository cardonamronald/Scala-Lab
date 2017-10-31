package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math.min

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      one <- arbitrary[A]
      rest <- oneOf(const(empty), genHeap)
    } yield insert(one, rest)
  )

  /*lazy val genNode: Gen[Node] = for {
    x <- arbitrary[Int]
    r <- arbitrary[Int]
    c <- oneOf(const(List.empty), Arbitrary.arbitrary[List[genNode]])
  } yield Node(x, r, c)*/

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)
  //implicit lazy val arbNode: Arbitrary[Node] = Arbitrary(genNode)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("Bogus1 - Insercion Correcta") = forAll { (h: H) =>
    val min = if (isEmpty(h)) 0 else findMin(h)
    deleteMin(h)
    findMin(insert(min, h)) == min
  }

  property("Hint1") = forAll{ (a: A, b: A) =>
    findMin(insert(a, insert(b, empty))) == min(a, b)
  }

  property("Hint2") = forAll{ (n: A) =>
    isEmpty(deleteMin(insert(n, empty)))
  }

  property("Hint3") = forAll { (h: H) =>
    def secuencia(h1: H): List[Int] =
      if (isEmpty(h1)) List.empty else findMin(h1) :: secuencia(deleteMin(h1))

    def isSorted(list: List[Int], last: Int): Boolean = list match {
      case Nil => true
      case x :: Nil => last <= x
      case x :: xs => last <= x && isSorted(xs, x)
    }
    isSorted(secuencia(h).tail, secuencia(h).head)
  }

  property("Hint4") = forAll{ (h1: H, h2: H) =>
    findMin(meld(h1, h2)) == findMin(h1) ||
      findMin(meld(h1, h2)) == findMin(h2)
  }
}