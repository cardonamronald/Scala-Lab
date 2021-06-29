import java.util.concurrent.BlockingQueue
import scala.collection.mutable

class PoolThreadRunnable(taskQueue: BlockingQueue[Runnable]) extends Runnable {
  private val thread: Thread = Thread.currentThread();
  private var isStopped: Boolean = false

  override def run(): Unit = {
    while (!isStopped) {
      try {
        val runnable: Runnable = taskQueue.take()
        runnable.run()
      }
      catch {
        case e: Throwable => println(s"Failed to dequeue tasks.\n" + e.printStackTrace())
      }
    }
  }

  def doStop(): Unit = {
    isStopped = true
    thread.interrupt()
  }

  def getIsStopped: Boolean = isStopped
}
