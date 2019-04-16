package classes
class CheckSumAccumulator {

  private var sum = 0

  def add(d: Byte): Unit = sum += d // A method that is executed only for
  // its side effects is known as a procedure
  def ckecksum(): Int = ~(sum & 0xFF) + 1
}