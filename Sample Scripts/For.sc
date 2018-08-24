//Cualquier objeto en Scala que tenga FlatMap, Map, Filter, puede ser
//Traverse con for-comprehesions
for {
  i <- 0 to 10
  j <- 0 to 10
  if(i != j)
} yield s"$i * $j = ${i * j}\n"
