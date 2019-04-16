//Val can't be reassigned
val greetStrings: Array[String] = new Array[String](3)

greetStrings(0) = "Hello"
greetStrings(1) = ","
greetStrings(2) = "World!"
//The object will no change but the contents of the object will

for (i <- 0 to 2) println(greetStrings(i))

val greetBetter = Array("Hello", ",", "World!")

for (i <- 0.to(2)) Console println greetBetter.apply(i)

//All operations are method calls in Scala
1 + 2 == (1).+(2) //+ is a method

greetStrings(0) = "Hello"
greetStrings.update(0, "Hello")

val numNames = Array("zero", "one", "two")

