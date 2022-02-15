package calculator.tasks

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, collect_list, count, desc, rank, sum}

class PartThree extends BaseTask("PartThree") {
  lazy val spark: SparkSession = SparkSession.builder.getOrCreate()

  def processData(): Unit = {

    val clicksDF = spark.read
      .option("multiline", "true")
      .json(taskConf.getString("input_path.clicks_path"))
    val impressionDF = spark.read
      .option("multiline", "true")
      .json(taskConf.getString("input_path.impressions_path"))
      .na.drop(Seq("country_code", "app_id")).filter(col("country_code") =!= "")

    val sumRevenuePerImpression = clicksDF.groupBy("impression_id").agg(sum("revenue").as("sum_revenue_per_impression_id"))
    val partialDF = impressionDF
      .join(sumRevenuePerImpression, sumRevenuePerImpression("impression_id") === impressionDF("id"), "right")
      .drop("id")
      .groupBy("app_id", "country_code", "advertiser_id")
      .agg((sum("sum_revenue_per_impression_id") / count("impression_id")).as("rate"))

    val window = Window.partitionBy("app_id", "country_code")
      .orderBy(desc("rate"))

    val rankedDF = partialDF
      .withColumn("rank", rank().over(window))
      .filter(col("rank") <= 5)
      .withColumn("recommended_advertiser_ids", collect_list(col("advertiser_id")).over(window))
      .drop("advertiser_id", "rate", "rank")

    rankedDF.coalesce(1).write.mode("overwrite").json("Part3Res")

  }

}
