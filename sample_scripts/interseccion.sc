import scala.util.Random
case class Sem (name: String, var value: Int) {
  def setVal(value : Int): Unit = this.value = value
}

def wait_S(sem: Sem): Unit = {
  if(sem.value <= 0) wait_S(sem) else sem.setVal(sem.value - 1)
}

def signal_S(sem: Sem): Unit = sem.setVal(sem.value + 1)

val mutex: Sem =  Sem("Interseccion", 1)

class Car(mutex: Sem) {
  def run(): Unit ={
    wait_S(mutex)
    pasar()
    signal_S(mutex)
  }

  def pasar(): Unit = println("Estoy pasando, Wiiii :)")
}

val cl: List[Car] = Nil
val kr: List[Car] = Nil