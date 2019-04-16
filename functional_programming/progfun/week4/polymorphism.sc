trait Printable {
  def toString2: String
}

def printElementsInLines(elements: Printable*): Unit = {
  elements foreach(e => println(e.toString2))
}

final case class User(name: String, age: Int) extends Printable {
  override def toString2: String = s"User {name: '$name', age: '$age'}"
}

printElementsInLines(
  User(name = "Ronald", age = 20)
)