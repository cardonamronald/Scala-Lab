package Week4
object observers {
  val a = new BankAccount
  val b = new BankAccount
  val consolidator = new Consolidator(List(a, b))
}