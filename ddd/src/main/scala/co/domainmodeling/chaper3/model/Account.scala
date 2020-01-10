package co.domainmodeling.chaper3.model

import java.util.Date

import co.domainmodeling.chaper3.lens.Lens

import util.{Failure, Success, Try}
import common._

sealed trait Account {
  def no: String

  def name: String

  def dateOfOpen: Option[Date]

  def dateOfClose: Option[Date]

  def balance: Balance
}


// private prevents calling the default apply constructor and final prevents any inheritance.
final case class CheckingAccount private (no: String, name: String, dateOfOpen: Option[Date], dateOfClose: Option[Date],
                                          balance: Balance) extends Account

final case class SavingsAccount private (no: String, name: String, rateOfInterest: Amount, dateOfOpen: Option[Date],
                                         dateOfClose: Option[Date], balance: Balance) extends Account

object Account {
  def checkingAccount(no: String, name: String, openDate: Option[Date], closeDate: Option[Date],
                      balance: Balance): Try[Account] = {
    closeDateCheck(openDate, closeDate).map { d =>
      CheckingAccount(no, name, Some(d._1), d._2, balance)
    }
  }

  def savingsAccount(no: String, name: String, rate: BigDecimal, openDate: Option[Date], closeDate: Option[Date],
                     balance: Balance): Try[Account] = {
    closeDateCheck(openDate, closeDate).map { d =>
      if (rate <= BigDecimal(0))
        throw new Exception(s"Interest rate $rate must be > 0")
      else
        SavingsAccount(no, name, rate, Some(d._1), d._2, balance)
    }
  }

  private def closeDateCheck(openDate: Option[Date],
                             closeDate: Option[Date]): Try[(Date, Option[Date])] = {
    val od = openDate.getOrElse(today)
    closeDate.map { cd =>
      if (cd before od) Failure(new Exception(
        s"Close date [$cd] cannot be earlier than open date [$od]"))
      else Success((od, Some(cd)))
    }.getOrElse {
      Success((od, closeDate))
    }
  }
}

trait CheckingAccountLenses {
  val accountBalanceLenses: Lens[CheckingAccount, Balance] =
    Lens[CheckingAccount, Balance](
      get = _.balance,
      set = (checkingAccount, balance) => checkingAccount.copy(balance = balance)
    )
}

trait SavingsAccountLenses {
  val accountBalanceLenses: Lens[SavingsAccount, Balance] =
    Lens[SavingsAccount, Balance](
      get = _.balance,
      set = (savingsAccount, balance) => savingsAccount.copy(balance = balance)
    )
}