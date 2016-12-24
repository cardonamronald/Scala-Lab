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
balance("Hola)".toList)