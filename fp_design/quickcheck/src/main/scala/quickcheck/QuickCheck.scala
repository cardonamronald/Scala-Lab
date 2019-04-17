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
      a <- arbitrary[A]
      rest <- oneOf(const(empty), genHeap)
    } yield insert(a, rest))

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)


  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("gen1") = forAll { h: H =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("insert any two elements into an empty heap") =
    forAll { (a: A, b: A) =>
      findMin(insert(a, insert(b, empty))) == min(a, b)
    }

  property("insert an element into an empty heap") =
    forAll { a: A =>
      isEmpty(deleteMin(insert(a, empty)))
    }

  property("sorted") =
    forAll { heap: H =>
      def sorted(heap: H, lastItem: Int): Boolean = heap match {
        case empty => true
        case _ =>
          if (lastItem <= findMin(heap)) sorted(deleteMin(heap), findMin(heap))
          else false
      }

      heap match {
        case empty => true
        case _ => sorted(deleteMin(heap), findMin(heap))
      }
    }

  property("associative meld") = forAll { (h:H, i:H, j:H) =>
    val a = meld(meld(h, i), j)
    val b = meld(h, meld(i, j))
    toList(a) == toList(b)
  }

  property("melding minima") = forAll {(h1: H, h2: H) =>
    val a = if (isEmpty(h1)) 0 else findMin(h1)
    val b = if (isEmpty(h2)) 0 else findMin(h2)

    val merge = meld(h1, h2)
    if (isEmpty(merge)) true
    else findMin(merge) == a || findMin(merge) == b
  }

  def toList(h: H): List[Int] =
    if (isEmpty(h)) Nil
    else findMin(h) :: toList(deleteMin(h))
}