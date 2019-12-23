package co.domainmodeling.chaper3.service

import co.domainmodeling.chaper3.Lens
import co.domainmodeling.chaper3.Lens._

case class Address(no: String, street: String, city: String, state: String, zip: String)

case class Customer(id: Int, name: String, address: Address)

object Main extends App {
  val addressLenses: Lens[Address, String] = Lens[Address, String] (
    get = _.no,
    set = (o, v) => o.copy(no = v)
  )

  val custAddressLens = Lens[Customer, Address](
    get = _.address,
    set = (o, v) => o.copy(address = v)
  )


  val a = Address(no = "B-12", street = "Monroe Street", city = "Denver", state = "CO", zip = "80231")
  val c = Customer(12, "John D Cook", a)

  val custAddrNoLens = compose(custAddressLens, addressLenses)

  custAddrNoLens.get(c)
  custAddrNoLens.set(c, "B675")
}
