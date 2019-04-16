package BankAccountWithEventHandling
trait Subscriber {
  def handler(pub: Publisher)
}
