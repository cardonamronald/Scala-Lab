val n = 6
for {
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)

(1 until n) flatMap( i =>
  (1 until i).withFilter(j => isPrime(i + j))
    .map(j => (i, j))
  )
def isPrime(a: Int): Boolean = true

case class Book (title: String, authors: List[String])

val books: Set[Book] = Set (
  Book(title = "Structure and Interpretation of Computer Programs", authors = List("Abelson, Harald", "Sussman, Gerald J.")),
  Book(title = "Introduction to Functional Programming", authors = List("Bird, Richard", "Wadler, Phil")),
  Book(title = "Effective Java", authors = List("Bloch, Joshua")),
  Book(title = "Effective Java", authors = List("Bloch, Joshua")),
  Book(title = "Java Puzzlers", authors = List("Bloch, Joshua", "Gafer, Neal")),
  Book(title = "Programming in Scala", authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill"))
)
for (b <- books; a <- b.authors if a startsWith "Bloch") yield b.title

books.flatMap(x => for(b <- x.authors; if b.startsWith("Bloch")) yield b)

books.flatMap(book => book.authors withFilter(authors => authors.startsWith("Bloch")) map ())




