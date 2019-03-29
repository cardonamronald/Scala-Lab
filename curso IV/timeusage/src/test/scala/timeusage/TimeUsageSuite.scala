package timeusage

import org.apache.spark.sql.{ColumnName, DataFrame, Row}
import org.apache.spark.sql.types.{
  DoubleType,
  StringType,
  StructField,
  StructType
}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.util.Random
import TimeUsage._

@RunWith(classOf[JUnitRunner])
class TimeUsageSuite extends FunSuite with BeforeAndAfterAll {

  lazy val (columns, initDf) = read("/timeusage/atussum.csv")
  lazy val (primaryNeedsColumns, workColumns, otherColumns) = classifiedColumns(columns)
  lazy val summaryDf = timeUsageSummary(primaryNeedsColumns, workColumns, otherColumns, initDf)
  lazy val finalDf = timeUsageGrouped(summaryDf)

  def initializeTimeUsage(): Boolean =
    try {
      TimeUsage
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  override def afterAll(): Unit = {
    assert(initializeTimeUsage(), " -- did you set all the values in TimeUsage SparkSession ?")

    spark.stop()
  }

  test("'initDf' and 'columns' from csv header must be the same") {
    assert(initializeTimeUsage(), " -- did you set all the values in TimeUsage SparkSession ?")
    assert(initDf.schema.toList.map(_.name) === columns)
  }

  test("classifiedColumns and columns lenght must be the same") {
    assert(initializeTimeUsage(), " -- did you set all the values in TimeUsage SparkSession ?")
    assert((primaryNeedsColumns ::: workColumns ::: otherColumns).length == columns.length)
  }

  test("summaryDf") {
    assert(true)
  }
}