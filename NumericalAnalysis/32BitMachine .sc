def parseDecToBin(n: Int, word: String): String = {
  n match {
    case 0 => "0" + word
    case 1 => "1" + word
    case _ => parseDecToBin(n / 2, (n % 2) + word)
  }
}
parseBinToInt(parseDecToBin(30, "").toList, 0)


def parseBinToInt(word: List[Char], i: Int): Int = {
  val w: List[Char] = word.reverse
  w match {
    case '0' :: Nil => 0
    case '0' :: Nil => 0
    case '1' :: xs => 2^i + parseBinToInt(xs, i+1)
    case '1' :: xs => 2^i + parseBinToInt(xs, i+1)
  }
}

/**def parseBinToDec(word: String): String = {
  val w: List[Char] = word.toList

}**/