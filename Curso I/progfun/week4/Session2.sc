abstract class Natural {
  def isZero : Boolean

  def predecessor : Natural

  def sucessor : Natural = new Succ(this)

  def + (that : Natural) : Natural
  def - (that : Natural) : Natural
}

object Zero extends Natural {
  def isZero = true

  def predecessor : Natural = throw new Error("isZero")

  def + (that : Natural) = that

    def - (that : Natural) = if (that.isZero) this
      else throw new Error ("isNegative")
}

class Succ (n : Natural) extends Natural {
  def isZero : Boolean = false

  def predecessor : Natural = n

  def + (that : Natural) : Natural = new Succ(n + that)

  def - (that : Natural) = if (that.isZero) this
    else n - that.predecessor
}