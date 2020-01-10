package co.domainmodeling.chaper3.model

import java.util.{Calendar, Date}

object common {
  type Amount = BigDecimal

  def today: Date = Calendar.getInstance.getTime

  // This is a value object
  case class Balance(amount: Amount = 0)
}
