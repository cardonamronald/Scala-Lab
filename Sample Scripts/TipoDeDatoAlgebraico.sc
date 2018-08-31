//Option nos ayuda a representar valores que pueden o no pueden estar
sealed trait Option[T] { //Todos los hijos de esta clase existen en esta unidad de compilacion (archivo)
  def getOrElse[T](sino: T): T
}

final case class Some[T](t: T) extends Option[T] {
  override def getOrElse[T](sino: T): T = t
}

case object None extends Option[Nothing] {
  override def getOrElse[T](sino: T): T = sino
}

def getOrFive(option: Option[Int]): Int = option match {
  case x: Some[Int] => x.getOrElse(5)
}