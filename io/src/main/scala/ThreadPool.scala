import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}
import scala.collection.mutable

class ThreadPool(noThreads: Int, maxNoTasks: Int) {
  private val taskQueue: BlockingQueue[Runnable] = new ArrayBlockingQueue[Runnable](maxNoTasks)
  
  private var isStopped: Boolean = false
  
  private val runnables: IndexedSeq[PoolThreadRunnable] = for {
    _ <- 0 to noThreads
  } yield new PoolThreadRunnable(taskQueue)

  runnables foreach (runnable => new Thread(runnable).start())

  def execute(task: Runnable): Unit = {
    if (isStopped) throw new IllegalStateException("Thread pool is stopped!")
    else taskQueue.offer(task)
  }

  def stop(): Unit = {
    isStopped = true
    runnables foreach (_.doStop())
  }

  def waitUntilAllTasksFinished(): Unit = {
    while (taskQueue.size() > 0) {
      try {
        Thread.sleep(1)
      }
      catch {
        case e: Throwable => e.printStackTrace()
      }
    }
  }
}