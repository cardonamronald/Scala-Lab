package timeusage

import org.apache.spark.sql.{ColumnName, DataFrame, Dataset, Row}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.util.Random
import TimeUsage._

@RunWith(classOf[JUnitRunner])
class TimeUsageSuite extends FunSuite with BeforeAndAfterAll {

  lazy val (columns, initDf) = read("/timeusage/atussum.csv")
  lazy val (primaryNeedsColumns, workColumns, otherColumns) = classifiedColumns(columns)
  lazy val summaryDf: DataFrame = timeUsageSummary(primaryNeedsColumns, workColumns, otherColumns, initDf)
  lazy val finalDf : DataFrame = timeUsageGrouped(summaryDf)
  lazy val usingSqlAPI: DataFrame = timeUsageGroupedSql(summaryDf)
  lazy val usingDataSets: Dataset[TimeUsageRow] = timeUsageSummaryTyped(summaryDf)

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

  test("SQL API and DataFrames resuls must me the same") {
    assert(initializeTimeUsage(), " -- did you set all the values in TimeUsage SparkSession ?")
    usingSqlAPI.except(finalDf)
  }

  test("Datasets API and DataFrame's resuls must me the same") {
    assert(initializeTimeUsage(), " -- did you set all the values in TimeUsage SparkSession ?")
    usingDataSets.toDF.except(finalDf)
  }
}