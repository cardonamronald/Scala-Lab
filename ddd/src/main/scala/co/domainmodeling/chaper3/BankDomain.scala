package co.domainmodeling.chaper3

import java.util.{Calendar, Date}

import scala.util.{Failure, Success, Try}

object BankDomain { 
  type Amount = BigDecimal

  def today: Date = Calendar.getInstance.getTime
  case class Balance(amount: Amount = 0)
  case class Account(no: String, name: String, dateOfOpen: Date,
                     dateOfClose: Option[Date] = None, balance: Balance = Balance(0))

  trait AccountService [Account, Amount, Balance] {
    def open(no: String, name: String, openDate: Option[Date]): Try[Account]
    def close(account: Account, closeDate: Option[Date]): Try[Account]
    def debit(account: Account, amount: Amount): Try[Account]
    def credit(account: Account, amount: Amount): Try[Account]
    def balance(account: Account): Try[Balance]

    def transfer(from: Account, to: Account, amount: Amount): Try[(Account, Account, Amount)] = for {
      _ <- debit(from, amount)
      _ <- credit(to, amount)
    } yield (from, to, amount)
  }

  object AccountService extends AccountService[Account, Amount, Balance] {
    override def open(no: String, name: String, openingDate: Option[Date]): Try[Account] = {
      if (no.isEmpty || name.isEmpty)
        Failure(new Exception(s"Account no or name cannot be blank") )
      else if (openingDate.getOrElse(today) before today)
        Failure(new Exception(s"Cannot open account in the past"))
      else Success(Account(no, name, openingDate.getOrElse(today)))
    }

    override def close(account: Account, closeDate: Option[Date]): Try[Account] = {
      val cd = closeDate.getOrElse(today)
      if (cd before account.dateOfOpen)
        Failure(new Exception(s"Close date $cd cannot be before opening date ${account.dateOfOpen}"))
      else Success(account.copy(dateOfClose = Some(cd)))
    }

    override def debit(a: Account, amount: Amount): Try[Account] = {
      if (a.balance.amount < amount)
        Failure(new Exception("Insufficient balance"))
      else Success(a.copy(balance = Balance(a.balance.amount - amount)))
    }

    override def credit(a: Account, amount: Amount): Try[Account] =
      Success(a.copy(balance = Balance(a.balance.amount + amount)))

    override def balance(account: Account): Try[Balance] = Success(account.balance)
  }
}
