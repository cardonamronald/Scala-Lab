package co.domainmodeling.chaper3.model

import org.scalacheck.Properties

object AccountLensesSpecification extends Properties("AccountLenses") {
  property("Identity — If you get and then set back with the same value, the object remains unchanged.")

  property("Retention — If you set with a value and then perform a get, you get what you had set with.")

  property("Double set — If you set twice in succession and then perform a get, you get back the last set value.")
}