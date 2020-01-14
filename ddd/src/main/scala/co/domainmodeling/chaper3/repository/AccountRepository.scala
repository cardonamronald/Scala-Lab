package co.domainmodeling.chaper3.repository

import java.util.Date

import co.domainmodeling.chaper3.model.Account
import co.domainmodeling.chaper3.model.common.Balance

import collection.mutable.{Map => MMap}

import scala.util.{Failure, Success, Try}

trait AccountRepository extends Repository[Account, String] {
  override def query(id: String): Try[Option[Account]]

  override def store(a: Account): Try[Account]

  def balance(accountNo: String): Try[Balance] = query(accountNo) match {
    case Success(Some(value)) => Success(value.balance)
    case Success(None)        => Failure(new Exception(s"No account exists with no $accountNo"))
    case Failure(exception)   => Failure(exception)
  }

  def query(openedOn: Date): Try[Seq[Account]]
}

trait AccountRepositoryInMemory extends AccountRepository {
  lazy val repo = MMap.empty[String, Account]

  def query(no: String): Try[Option[Account]] = Success(repo.get(no))

  def store(a: Account): Try[Account] = {
    val r = repo += ((a.no, a))
    Success(a)
  }

  def query(openedOn: Date): Try[Seq[Account]] = Success {
    repo.values.iterator.filter(_.dateOfOpen.contains(openedOn)).toSeq
  }
}

object AccountRepositoryInMemory extends AccountRepositoryInMemory
