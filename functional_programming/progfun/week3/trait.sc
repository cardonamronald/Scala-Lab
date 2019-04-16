//Is like an abstract class
//classes, objects and traits can inherit from at most one class
//but arbitrary many traits
//Traits can contain fields and methods
//Traits never have parameters
trait Planar {
  def height: Int
  def width: Int
  def surface: Int = height * width
}
