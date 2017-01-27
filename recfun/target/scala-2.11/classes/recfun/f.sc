def sum(f: Int => Int)(a: Int, b: Int): Int = {
  def loop(a: Int, acc: Int): Int = {
    if (a > b) 0
    else loop(a + 1, b)
  }
  loop(a, b)
}
