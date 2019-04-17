/**
  * Example taken by the reactive programming course by Martin Odesky.
  */
import Week3._

object test extends App {
  object sim extends Circuits with Parameters
  import sim._
  val in1, in2, sum, carry = new Wire
  halfAdder(in1, in2, sum, carry)
  probe("sum", sum)
  probe("carry", carry)
  in2 setSignal true
  run
  in2 setSignal true
  run
}