package BankAccountWithEventHandling

import scala.collection.mutable

class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this))

  private var total: Int = _ // Uninitialized.
  compute()

  private def compute(): Unit =
    total = observed.map(_.currentBalance).sum(implicitly[Numeric[Int]])

  def handler(pub: Publisher): Unit = compute()

  def totalBalance: Int = total
}