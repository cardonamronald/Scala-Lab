//Turn command line args to uppercase
def main (args: Array[String]): Unit = {
  val res = for (a <- args) yield println (a.toUpperCase)
}