package co.domainmodeling.chaper3.service

import co.domainmodeling.chaper3.lens.Lens
import co.domainmodeling.chaper3.lens.Lens._
import co.domainmodeling.chaper3.model._
import co.domainmodeling.chaper3.model.common.Balance
import common._

object Main extends App
  with CustomerLenses
  with AddressLenses
  with CheckingAccountLenses {

  val a: Address = Address(no = "B-12", street = "Monroe Street", city = "Denver", state = "CO", zip = "80231")
  val c: Customer = Customer(12, "John D Cook", a)

  val custAddrNoLens: Lens[Customer, String] = compose(custAddressLens, noLenses)

  println(custAddrNoLens.get(c))
  println(custAddrNoLens.set(c, "B675"))


  val account = CheckingAccount("55757827742", "Ronald Cardona", Some(today), None, Balance(250))


  println(accountBalanceLenses.set(account, Balance(33)))
}