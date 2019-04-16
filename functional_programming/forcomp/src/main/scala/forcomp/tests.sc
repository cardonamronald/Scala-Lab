type Word = String
type Sentence = List[Word]
type Occurrences = List[(Char, Int)]
def wordOccurrences(w: String): Occurrences =
  w.toLowerCase.groupBy(c => c).map(x => x._1 -> x._2.length).toList.sorted

def sentenceOccurrences(s: Sentence): Occurrences = {
  val a = for {
    word <- s
    occurrence <- wordOccurrences(word)
  } yield occurrence
  a.groupBy(_._1).mapValues(_.length).toList.sorted
}
val occurrences = List(('a', 2), ('b', 2))
val occs: List[Occurrences] = occurrences.map(x => (for (i <- 1 until (x._2 + 1)) yield (x._1, i)).toList)
occs





def combinations(occurrences: Occurrences): List[Occurrences] = {
  val occs: List[Occurrences] = occurrences.map(x => (for (i <- 1 until (x._2 + 1)) yield (x._1, i)).toList)
  occs.foldRight(List[Occurrences](Nil))((x, y) => y ++ (
    for {
      i <- x
      j <- y
    } yield i :: j))
}

def combinations_1(occurrences: Occurrences): List[Occurrences] = {
  occurrences match {
    case List() => List(List())
    case head :: tail =>
      val tailCombine = combinations(tail)
      tailCombine ++ (
        for {
          occurrence <- tailCombine
          index <- 1 to head._2
        } yield (head._1, index) :: occurrence
        )
  }
}

def subtract1(x: Occurrences, y: Occurrences): Occurrences = {
  val aux = for {
    (charOcc1, intOcc1) <- x
    (charOcc2, intOcc2) <- y
    if charOcc1 == charOcc2
  } yield (charOcc1, intOcc1 - intOcc2)
  aux.filter(_._2 > 0)
}

def subtract(x: Occurrences, y: Occurrences): Occurrences = {
  def aux(x:Occurrences, y: (Char, Int)): List[(Char, Int)] = {
    for {
      occurrence <- x
    } yield (occurrence._1, if (occurrence._1 == y._1) occurrence._2 - y._2 else occurrence._2)
  }
  y.foldLeft(x)(aux).filter(_._2 > 0)
}