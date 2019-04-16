/**
  * Created by RONALD on 10/08/2018.
  */
import scala.io.Source
import scala.io._

def widthOfLength(s: String) = s.length.toString.length

if(args.length > 0) {
  val myFile = new File(args(0))
  val lines = Source.fromFile(myFile).getLines().toList

  val longestLine = lines.reduceLeft(
    (a, b) => if(a.length > b.length) a else b
  )
  val maxWidth = widthOfLength(longestLine)

  for (line <- lines) {
    val numSpaces = maxWidth - widthOfLength(line)
    val padding = " " * numSpaces
    println(padding + line.length + " | " + line)
  }
}
else
  Console.err.println("Please enter filename")