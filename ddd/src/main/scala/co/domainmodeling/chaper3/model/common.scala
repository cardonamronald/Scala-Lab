package co.domainmodeling.chaper3.model

import java.util.{Calendar, Date}

object common {
  type Amount = BigDecimal
  def today: Date = Calendar.getInstance.getTime
  case class Balance(amount: Amount = 0)
}
