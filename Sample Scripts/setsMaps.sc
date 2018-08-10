//Arrays are always mutable
//Lists are always immutable
var jetSet = Set("Boeing", "Airbus") // Immutable, var
jetSet += "Lear"
println(jetSet.contains("Cessna"))

import scala.collection.mutable

val movieSet = mutable.Set("Hitch", "Poltergeist") // Mutable, val
movieSet += "Shrek"
println(movieSet)

import scala.collection.immutable.HashSet

val hashSet = HashSet("Tomatoes", "Chillies")
println(hashSet + "Coriander")