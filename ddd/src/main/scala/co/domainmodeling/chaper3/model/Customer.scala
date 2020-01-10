package co.domainmodeling.chaper3.model

import co.domainmodeling.chaper3.lens.Lens

case class Customer(id: Int,
                    name: String,
                    address: Address)


trait CustomerLenses {
  val custAddressLens: Lens[Customer, Address] =
    Lens[Customer, Address] (
      get = _.address,
      set = (o, v) => o.copy(address = v)
    )
}