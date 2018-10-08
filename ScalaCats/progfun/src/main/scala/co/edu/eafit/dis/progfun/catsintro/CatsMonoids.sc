//Monoids in Cats

//The type classes
import cats.Monoid
import cats.Semigroup


//Instances
import cats.Monoid
import cats.instances.string._  // for Monoid
Monoid[String].combine("Hey", "there")

import cats.Monoid
import cats.instances.int._ // for Monoid
Monoid[Int].combine(32, 10)