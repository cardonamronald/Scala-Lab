package standardscala

case class TempData(day: Int, doy: Int, month: Int, year: Int, precip: Double,
                    snow: Double, tave: Double, tmax: Double, tmin: Double)

object TempData {
  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromFile("resources/MN212142_9392.csv")
    val lines: Iterator[String] = source.getLines().drop(1)
    val data = lines.filterNot(x => x.contains(",.,") || x.contains("'")).map { line =>
      val p = line.split(',')
      TempData(p(0).toInt, p(1).toInt, p(2).toInt, p(3).toInt, p(4).toDouble,
        p(5).toDouble, p(6).toDouble, p(7).toDouble, p(8).toDouble)

    }.toArray
    source.close()
    data.take(5) foreach println
  }
}
