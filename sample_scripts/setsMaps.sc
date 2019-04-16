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

import scala.collection.mutable
val treaseureMap = mutable.Map[Int, String]()
treaseureMap += (1 -> "Go to island")
treaseureMap += (2 -> "Find big X on ground")
treaseureMap += (3 -> "Dig.")
println(treaseureMap(2))

//Immutable maps are deafult maps, no import necessary
val romanNumeral = Map(
  1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
)
println(romanNumeral(4))