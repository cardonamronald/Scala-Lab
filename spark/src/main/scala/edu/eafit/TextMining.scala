package edu.eafit

import java.nio.file.Paths

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object TextMining {
  /** Main function */
  def main(args: Array[String]) = {
    @transient lazy val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("BigData")
    //@transient lazy val sparkContext: SparkContext = new SparkContext(conf)

    val spark: SparkSession =
      SparkSession
        .builder
        .master("local[*]")
        .appName("Text Mining")
        .getOrCreate()

    val sc = spark.sparkContext
    sc.setLogLevel("WARN")

    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    // Read the data.
    val newsDF: DataFrame = // apartments: DataFrame = [name: string, area: double, price double].
      spark
        .read
        .option(key = "header", value = "true")
        .option(key = "encoding", value = "UTF-8")
        .option(key = "sep", value = ",")
        .option(key = "inferSchema", value = "true")
        .csv("src/main/resources/all-the-news/articles1.csv",
                      "src/main/resources/all-the-news/articles2.csv",
                      "src/main/resources/all-the-news/articles3.csv")
    println(newsDF.count())

    // No comments here.
    spark.stop()
  }

  val schema = StructType(List(
    StructField("id",StringType, true),
    StructField("title", StringType, true),
    StructField("content", StringType, true)))

  case class Article(id: String, title: String, content: String)

  ///** @return The filesystem path of the given resource */
  //def fsPath(resource: String): String =
    //Paths.get(this.getClass.getResource(resource).toURI).toString

  def dropHeaders(data: RDD[String]): RDD[String] = {
    data.mapPartitionsWithIndex((i, lines) => {
      if(i == 0) {
        lines.drop(1)
      }
      lines
    })
  }
}