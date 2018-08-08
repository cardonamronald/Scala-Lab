val colors = Map ( "red" -> 0xFF0000,
                  "turquoise" -> 0x000000,
                  "black" -> 0x0100000,
                  "orange" -> 0xFF8040,
                  "brown" -> 0x804000)

def main(args: Array[String]) {
  for (name <- args) println(
    colors.get(name) match {
      case Some(code) =>
        name + " has code: " + code
      case None =>
        "Unknown color: " + name
    }
  )
}
val args = Array("pink")
main(args)