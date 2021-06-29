@main def main: Unit=
  val threadPool: ThreadPool = new ThreadPool(3, 10)
  for {
    taskNo <- 0 to 10
  } yield threadPool.execute(() => println(s"${Thread.currentThread().getName}: Task $taskNo"))
  threadPool.waitUntilAllTasksFinished()
  threadPool.stop()