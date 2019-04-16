/**
  * Created by RONALD on 01/11/2017.
  */
package BankAccountWithSignals

class BankAccount {
  val balance = Var(0)
  def deposit(amount: Int): Unit {
    if (amount > 0) {
      val b = balance()
      balance() = b + amount
    }
  }
  def withDraw(amount: Int): Unit =
    if (0 < amount && amount <= balance()) {
      val b = balance()
      balance() = b - amount
    } else  throw new Error("Insufficient funds")
}