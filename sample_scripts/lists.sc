//Arrays are mutable
//Objects like Lists are immutable
val oneTwo = List(1, 2)
val threeFour = List(3, 4)
val oneTwoThreeFour = oneTwo ::: threeFour
println(oneTwo + " and " + threeFour + " were not mutated.")
println("Thus, " + oneTwoThreeFour + " is a new list")

val zeroOneTwo = 0 :: oneTwo
//:: es un metodo de la lista del operador de la derecha
val oneTwoThree = 1 :: 2 :: 3 :: Nil