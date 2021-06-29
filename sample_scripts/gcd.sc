import scala.annotation.tailrec

@tailrec
def mcd(a: Int, b: Int): Int = {
  print(s"mcd($a, $b)\n")
  if (a == b) a
  else if (a < b) mcd(a, b - a)
  else mcd(a - b, b)
}

mcd(mcd(1, 2), mcd(3, 4))
mcd(mcd(mcd(1, 2), 3), 4)
mcd(1, mcd(2, mcd(3, 4)))
mcd(mcd(1, mcd(2, 3)), 4)
