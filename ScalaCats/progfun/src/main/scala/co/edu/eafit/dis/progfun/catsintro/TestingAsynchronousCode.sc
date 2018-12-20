import scala.concurrent.Future
import cats.instances.future._
import cats.{Applicative, Id}
import cats.instances.list._
import cats.syntax.traverse._
import cats.syntax.functor._ // for map

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.higherKinds

trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}

trait RealUptimeClient extends UptimeClient[Future] {
  def getUptime(hostname: String): Future[Int]
}

/**trait TestUptimeClient extends UptimeClient[Id] {
  def getUptime(hostname: String): Id[Int]
}*/

class UptimeService[F[_]] (client: UptimeClient[F])
                          (implicit val applicative: Applicative[F]) {
  def getTotalUptime(hostnames: List[String]): F[Int] =
    hostnames.traverse(client.getUptime).map(_.sum)
}

class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
  def getUptime(hostname: String): Id[Int] =
    hosts.getOrElse(hostname, 0)
}

def testTotalUptime(): Unit = {
  val hosts = Map("host1" -> 10, "host2" -> 6)
  val client = new TestUptimeClient(hosts)
  val service = new UptimeService(client)
  val actual = service.getTotalUptime(hosts.keys.toList)
  val expected = hosts.values.sum
  assert(actual == expected)
}

testTotalUptime()