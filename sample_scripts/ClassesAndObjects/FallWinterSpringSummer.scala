import ChecksumAccumulator.calculate

object FallWinterSpringSummer extends App {
  for (season <- List("Fall", "Winter", "Spring", "Summer"))
    println(season + ": " + calculate(season))
}