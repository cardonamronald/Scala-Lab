package co.domainmodeling.chaper3.model

import co.domainmodeling.chaper3.lens.Lens

case class Address(no: String,
                   street: String,
                   city: String,
                   state: String,
                   zip: String)

trait AddressLenses {
  val noLenses: Lens[Address, String] =
    Lens[Address, String] (
      get = _.no,
      set = (o, v) => o.copy(no = v)
    )
}