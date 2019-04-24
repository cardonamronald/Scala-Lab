package BankAccountWithEventHandling

object observers extends App {
  val a = new BankAccount
  val b = new BankAccount
  val consolidator: Consolidator = new Consolidator(List(a, b))
  println(consolidator.totalBalance)
  a deposit 30
  println(consolidator.totalBalance)
  b deposit 20
  println(consolidator.totalBalance)
}