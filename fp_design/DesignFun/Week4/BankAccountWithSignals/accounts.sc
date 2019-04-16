package BankAccountWithSignals

object accounts {
  def consolidated(accts: List[BankAccount]): Signal[Int] =
    Signal(accts.map(_.balance()).sum)

  val a = new BankAccount
  val b = new BankAccount
  val c = consolidated(List(a, b))
  c()
  a deposit 20
  c()
  b deposit 30
  c()
  val xchange = Signal(246.00)
  val inDollar = Signal(c() * xchange())
  inDollar()
  B withDraw 10
  inDollar()
}