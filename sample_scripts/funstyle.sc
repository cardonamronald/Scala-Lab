//Imperative style
def printArgs(args: Array[String]): Unit = {
  var i = 0
  while (i < args.length) {
    println(args(i))
    i += 1
  }
}

//Functional Style
def printArgsFun(args: Array[String]): Unit = {
  for (arg <- args) println(arg)
}

//Functional-Hacker Style
def printArgsn(args:  Array[String]): Unit = {
  args foreach println
}

val s = Array("This", "is", "an", "example")
printArgs(s)
printArgsFun(s)
printArgsn(s)

//No vars, no side efects
def formatArgs(args: Array[String]) = args.mkString("\n")
