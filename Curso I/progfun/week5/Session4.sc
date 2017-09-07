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
val nums = List(-3,2,5,6,9,7,4,1,2,3,0,3,9,8,-5,-6,-3,-1,0,0,1,2)
val fruits = List("apple", "orange", "pear", "banana", "lemon")

nums filter(x => x < 0)

nums filterNot (x => x < 0)

nums partition(x => x < 0)

nums takeWhile(x => x < 0)

nums dropWhile(x => x < 0) //Reminder of takeWhile

nums span(x => x < 0) //take n' drop while