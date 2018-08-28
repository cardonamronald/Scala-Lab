//if, while, for, try, match and function calls
//Almost all of Scala's control structures result in some value
//If behaves like ternary op (?)
val args: List[String] = List("args.txt")

val filename: String = if (args.nonEmpty) args(0) else "default.txt"
