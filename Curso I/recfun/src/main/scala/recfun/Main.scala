package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || r == 1 || r == 0 || c == r) 1 else pascal(c, r - 1) + pascal(c - 1, r - 1)
  }


  /**
   * Exercise 2
  */
  def balance(chars: List[Char]): Boolean = {

    def count (arr : List[Char], cont : Int) : Int = {
      if (cont < 0) cont else {
        var c = cont
        if (arr.nonEmpty) {
          if (arr.head.equals('(')) c += 1
          if (arr.head.equals(')')) c -= 1
          count(arr.tail, c)
        }
        else cont
      }
    }
    if (chars.isEmpty) true else count(chars, 0) == 0
  }


    /**
   * Exercise 3
  */
  def countChange(money: Int, coins: List[Int]): Int = {

    }
}