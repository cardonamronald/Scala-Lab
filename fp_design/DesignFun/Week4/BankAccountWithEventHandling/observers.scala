package BankAccountWithEventHandling

object observers extends App {
  val a = new BankAccount
  val b = new BankAccount
  val consolidator: Consolidator = new Consolidator(List(a, b))
  consolidator.totalBalance
}