//L = L /R L = 	{y | ∃z such that yz ∈ L' ∧ z ∈ L"}
def rightQuotient (L : Set[String], L2 : Set[String]) : Set[String] = {

  def helper (L : Set[String], L2 : Set[String]) : Set[String] = {
    for {
        z <- L2
        yz <- L
        if (yz.endsWith(z))
    } yield yz.substring(0, yz.length - z.length)
  }
  helper(L, L2)
}

def leftQuotient (L : Set[String], L2 : Set[String]) : Set[String] = {

  def helper (L : Set[String], L2 : Set[String]) : Set[String] = {
    for {
      z <- L2
      zy <- L
      if (zy.startsWith(z))
    } yield zy.substring(z.length)
  }
  helper(L, L2)
}

def power (str : String, n : Int) : String = str * n

def lPower (original : Set[String], E : Set[String], n : Int) : Set[String] = {
  if (n == 0) E else {
    val e = for {
              str <- original
              str1 <- E
    } yield str.concat(str1)
    lPower(original, e, n - 1)
  }
}

def L7 = Set("∈10", "∈01")
def L10 = Set("∈", "0")
def L = Set("")

def listaAdy