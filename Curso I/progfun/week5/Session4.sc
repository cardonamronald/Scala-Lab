//Map operation o lists

def squareList (xs : List[Int]) : List[Int] = xs match {
  case List() => xs
  case y :: ys => y * y :: squareList(ys)
}
squareList(List(0,1,2,3,4,5,6))

/*def squareList (xs : List[Int]) : List[Int] = {
  xs map(x => x * x)
}*/


//Filtering
val nums = List(-3,2,5,6,9)
val fruits = List("apple", "orange", "pear", "banana", "lemon")

nums filter(x => x < 0)

nums filterNot (x => x < 0)

nums partition(x => x < 0)

nums takeWhile(x => x < 0)

nums dropWhile(x => x < 0) //Reminder of takeWhile

nums span(x => x < 0) //take n' drop while

def squareList1(xs: List[Int]): List[Int] = xs match {
  case Nil => xs
  case y :: ys => y*y :: squareList1(ys)
}

def squareListMap(xs: List[Int]): List[Int] = xs map (x => x*x)
val test: List[Int] = List(1, 2, 3, 4)
if (squareList(test) == squareList1(test) && squareList1(test) == squareListMap(test))
  println("Yaaay") else ":("


val list = List("a", "a", "a", "b", "c", "c", "a")
def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (first, rest) = xs span(y => y == x)
    first :: pack(rest)
}