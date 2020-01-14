package co.domainmodeling.chaper3.service.partial

import java.util.Date

import co.domainmodeling.chaper3.model.Account
import co.domainmodeling.chaper3.model.common.{Amount, Balance, today}
import co.domainmodeling.chaper3.repository.AccountRepository

import scala.util.{Failure, Success, Try}

trait AccountService[Account, Amount, Balance] {
  def open(no: String, name: String, openingDate: Option[Date]): AccountRepository => Try[Account]

  def close(no: String, closeDate: Option[Date]): AccountRepository => Try[Account]

  def debit(no: String, amount: Amount): AccountRepository => Try[Account]

  def credit(no: String, amount: Amount): AccountRepository => Try[Account]

  def balance(no: String): AccountRepository => Try[Balance]
}

object AccountService extends AccountService[Account, Amount, Balance] {
  override def open(no: String,
                    name: String,
                    openingDate: Option[Date]): AccountRepository => Try[Account] =
    (repo: AccountRepository) =>
      repo.query(no) match {
        case Success(_) => Failure(new Exception(s"Already existing account with no $no"))
        case Success(None) =>
          if (no.isEmpty || name.isEmpty)
            Failure(new Exception(s"Account no or name cannot be blank"))
          else if (openingDate.getOrElse(today) before today)
            Failure(new Exception(s"Cannot open account in the past"))
          else repo.store(Account.checkingAccount(no, name, openingDate, None, Balance()).get)
        case Failure(exception) =>
          Failure(new Exception(s"Failed to open account $no: $name", exception))
    }

  override def close(no: String, closeDate: Option[Date]): AccountRepository => Try[Account] = ???

  override def debit(no: String, amount: Amount): AccountRepository => Try[Account] = ???

  override def credit(no: String, amount: Amount): AccountRepository => Try[Account] = ???

  override def balance(no: String): AccountRepository => Try[Balance] = ???
}
