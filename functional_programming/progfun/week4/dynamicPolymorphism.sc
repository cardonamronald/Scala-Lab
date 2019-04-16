trait Printer[T] {
  def print(t : T): String
}

def printElementsInLines[T](elements: T*)(implicit TPrinter: Printer[T]): Unit = {
  elements foreach {e => println(TPrinter.print(e))}
}

final case class User(name: String, age: Int)

implicit val UserPrinter: Printer[User] = new Printer[User] {
  override def print(user: User): String = s"User {name: '${user.name}', age: '${user.age}'}"
}

printElementsInLines(
  User(name = "Andrew", age = 250)
)
