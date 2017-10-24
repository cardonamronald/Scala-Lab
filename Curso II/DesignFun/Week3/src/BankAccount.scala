package Week3
/**
  * Created by RONALD on 24/10/2017.
  */
class BankAccount {
  private var balance = 0
  def deposit(amount: Int): Unit {
    if (amount > 0) balance = balance + amount
  }
  def withDraw(amount: Int): Int =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else  throw new Error("Insufficient funds")
}