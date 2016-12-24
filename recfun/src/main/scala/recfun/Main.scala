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
    if (chars.isEmpty) true
    def count (arr : List[Char], cont : Int) : Int = {
      var c = cont
      if (!(arr.isEmpty)) {
        if (arr.head.equals('(')) c += 1
        if (arr.head.equals(')')) c -= 1
        count(arr.tail, c)
      }
      else cont

    }
    if (count(chars, 0) == 0) true else false
  }


    /**
   * Exercise 3
  */
  def countChange(money: Int, coins: List[Int]): Int = {
      var cont = 0

      def change (money : Int, acum : Int, coins : List[Int]) : Int = {
        if (money == 0 || coins.isEmpty) cont else {
          if (coins.head == money) cont += 1

          //'if (coins.head < money) acum += coins.head

          if ((coins.head + acum) == money) cont += 1

          if ((coins.head + acum) < money) {
            val v = acum + coins.head
            change(money, v, coins)
          }

          cont
        }
      }
      change(money, 0, coins)
    }
}