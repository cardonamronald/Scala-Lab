import scala.collection.mutable

object ChecksumAccumulator{

  private val cache = mutable.Map.empty[String, Int]

  def calculate(s: String): Int =
    if(cache.contains(s))
      cache(s)
    else {
      val acc = new CheckSumAccumulator // Instance of Classes.sc/ChecksumAccumulator Class (new for classes)
      for (c <- s)
        acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s -> cs)
      cs
    }
}

ChecksumAccumulator.calculate("Ronald Cardona Martinez")

class CheckSumAccumulator {

  private var sum = 0

  def add(d: Byte): Unit = sum += d // A method that is executed only for
  // its side effects is known as a procedure
  def checksum(): Int = ~(sum & 0xFF) + 1
}