package BankAccountWithSignals

// This code do not compile because the implementation
// if Signal class is not in scope

object accounts extends App {
  def consolidated(accts: List[BankAccount]): Signal[Int] =
    Signal(accts.map(_.balance()).sum(implicitly[Numeric[Int]]))

  val a = new BankAccount
  val b = new BankAccount
  val c = consolidated(List(a, b))

  print(c())
  a deposit 20
  print(c())
  b deposit 30
  print(c())

  val xchange = Signal(246.00)

  val inDollar = Signal(c() * xchange())

  inDollar()
  b withDraw 10
  print(inDollar())
}