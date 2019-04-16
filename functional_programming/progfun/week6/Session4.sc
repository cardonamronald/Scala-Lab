import scala.io.Source

object collections {
  val in = Source.fromURL("https://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords.txt")
  //val in = Source.fromFile("words.txt")

  val words = in.getLines.toList filter(word => word.forall(char => char.isLetter))

  /** Invert the mnem map to give a map from chars 'A'..'Z' to '2'..'9' */
  val mnem: Map[Char, String] = Map('2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL", '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

  /** invert the map the get a map of letters to digits */
  val charCode: Map[Char, Char] = for {
    (n, mnem) <- mnem
    char <- mnem
  } yield char -> n

  /** Maps a word to the digit string it can represent */
  def wordCode(word: String): String = word.map(x => charCode(x.toUpper))
                                        // word.toUpperCase map charCode

  /** A map from digit strings to the words that represent them */
  val wordsForNum: Map[String, Seq[String]] =
    words groupBy(word => wordCode(word)) withDefaultValue(Seq())

  /* function that receives a number and finds the words that match it */
  def encode(number: String): Set[List[String]] = {
    if (number.isEmpty) Set(List())
    else {
      for {
        split <- 1 to number.length
        word <- wordsForNum(number take split)
        rest <- encode(number drop split)
      } yield word :: rest
    }.toSet
  }

  encode("72252")

  def translate(number: String): Set[String] = encode(number) map (_.mkString(" "))

  translate("7225247386").foreach(println)
}