package edu.eafit

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

/**
  * TODO
  * Graph the results.
  * De los que usan x lenguaje, que librerias, herramientas, IDE, SO
  * */

object stackoverflow {
  @transient lazy val conf: SparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("BigData")

  @transient val spark: SparkSession =
    SparkSession
      .builder
      .master("local[*]")
      .appName("Text Mining")
      .getOrCreate()

  @transient val sc = spark.sparkContext
  sc.setLogLevel("WARN")

  val total: Int = 98855

  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._

  /** Main function */
  def main(args: Array[String]): Unit = {
    // Read the data.
    val df: DataFrame =
      spark
        .read
        .option(key = "header", value = "true")
        .option(key = "encoding", value = "UTF-8")
        .option(key = "sep", value = ",")
        .option(key = "inferSchema", value = "true")
        .csv("src/main/resources/survey_results_public.csv")

    val pro_devs: DataFrame =
      df.where("Employment != 'Not employed, but looking for work'")
        .where("FormalEducation != 'Primary/elementary school'")
        .where("FormalEducation != 'I never completed any formal education'")

    val topLangs: Array[String] = mostPopularLangs(pro_devs).keys.take(10)x

    spark.stop()
  }

  def geography(df: DataFrame): DataFrame = {
    val total = df.count()
    val dist = df.select($"Country")
      .groupBy($"Country")
      .count()
      .orderBy($"count".desc)

    dist.withColumn("percentage",
      dist.col("count").divide(total).multiply(100))
  }

  def students(df: DataFrame): DataFrame = {
    val total = df.select($"Student").count()
    val students = df.select($"Student")
      .groupBy("Student")
      .count()

    students
      .withColumn("percentage",
        students.col("count").divide(total).multiply(100))
  }

  def roles(df: DataFrame): RDD[(String, Int)] = {
    df.select($"DevType")
      .rdd
      .map(i => i.getAs[String](0))
      .map(i => i.split(";"))
      .flatMap(x => x.map(identity))
      .groupBy(identity)
      .mapValues(_.size)
      .sortBy(_._2, ascending = false)
  }

  def mostPopularLangs(df: DataFrame): RDD[(String, Int)] = {
    df.select($"LanguageWorkedWith")
      .rdd
      .map(i => i.getAs[String](0))
      .map(i => i.split(";"))
      .flatMap(x => x.map(identity))
      .groupBy(identity)
      .mapValues(_.size)
      .sortBy(_._2, ascending = false)
  }

  def gender(df: DataFrame): DataFrame = {
    df.select($"Gender")
      .groupBy("Gender")
      .count()
  }

  def age(df: DataFrame): DataFrame = {
    df.select($"Age")
      .groupBy("Age")
      .count()
  }

  def averageAgeByCountry(df: DataFrame) = {
    ???
  }

  def languageSpecific(df: DataFrame, lang: String): DataFrame = {
    val ers: DataFrame = df.filter(df("LanguageWorkedWith").contains(lang))
    val scalaStudents: DataFrame = students(ers)
    val scalaGeo: DataFrame = geography(ers)
    val otherTools: RDD[(String, Int)] = mostPopularLangs(ers)
    ???
  }
}