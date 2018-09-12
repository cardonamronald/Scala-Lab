def sum_1(a: List[Int]): List[Int] = {
  a match {
    case Nil => Nil
    case x :: xs => (x + 1) :: sum_1(xs)
  }
}
sum_1(List(1, 2, 3, 4, 5))

//Esto es un map
def applyF[T, G](a: List[T], f: T => G): List[G] = {
  a match {
    case Nil => Nil
    case x :: xs => f(x) :: applyF(xs, f)
  }
}
applyF(List(1, 2, 3), x => x + 1)

def applyG[T, G](a: List[T], f: T => G): List[G] = a.map(f)

//Leyes: No basta con definir las funciones por que cumplen
// unos axiomas
//Si aplico list.map(id) = list
//El tamaño de la lista de salida y la de entrada debe ser igual
//Map da la sensacion de iterar
//Filter como if dentro del ciclo
//FlatMap como un for dentro de un for

//Ejemplo, tablas de multiplicar


0 to 10 flatMap { i =>
 0 to 10 map (j => (i, j)) filter((i, j) => i * j)
}



/**0 to 10 flatMap{i =>
  (0 to 10) map {j =>
    (i, j)
  } filter {(i, j) => i != j} map {
    (i, j) => i * j
  }
}*/
