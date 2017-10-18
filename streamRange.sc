def streamRange(low: Int, high: Int): Stream[Int] = {
  print(low + " ")
  if (low >= high) Stream.empty
  else Stream.cons(low, streamRange(low + 1, high))
}
streamRange(1, 10)
streamRange(1, 10).toList
streamRange(1, 10).take(3).toList
