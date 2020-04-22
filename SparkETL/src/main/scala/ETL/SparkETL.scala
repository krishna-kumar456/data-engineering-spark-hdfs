package ETL

import play.api.libs.json.{JsValue, Json}
import scala.io.Source

object SparkETL {
  def main(args: Array[String]): Unit = {
    try {

      var jobConfigPath = ""
      if (args(0).isEmpty) {
        throw new Exception("jobConfig.json was not provided!")
      }
      else {
        jobConfigPath = args(0)
      }

      val jobConfig = Source.fromFile(jobConfigPath).getLines.mkString
      val jobConfigJson = Json.parse(jobConfig)

      val clusterInfo = jobConfigJson("cluster").as[JsValue]
      val clusterDetails = jobConfigClusterHandler()
      clusterDetails.appName = clusterInfo("appName").as[String]
      clusterDetails.masterIp = clusterInfo("masterIp").as[String]
      clusterDetails.logLevel = clusterInfo("logLevel").as[String]

      val sources = jobConfigJson("source").as[List[JsValue]]
      val destinations = jobConfigJson("destination").as[List[JsValue]]


      val spark = org.apache.spark.sql.SparkSession.builder
        .master(clusterDetails.masterIp)
        .appName(clusterDetails.appName)
        .getOrCreate

      spark.sparkContext.setLogLevel(clusterDetails.logLevel)

      println("Reading Source Files")
      for (source <- sources) {
        val sourceInfo = jobConfigSourceHandler()
        sourceInfo.sourceId = source("sourceId").as[String]
        sourceInfo.sourceType = source("sourceType").as[String]
        sourceInfo.fileType = source("fileType").as[String]
        sourceInfo.path = source("path").as[String]

        if (sourceInfo.sourceType == "hdfs") {
          sourceInfo.path = "hdfs://" + sourceInfo.path
        }

        val sourceDf = spark.read.format(sourceInfo.fileType).option("header", "true").load(sourceInfo.path)
        sourceDf.createOrReplaceTempView(sourceInfo.sourceId)

      }

      println("Loading files to destinations")
      for (destination <- destinations) {
        val destInfo = jobConfigDestinationHandler()
        destInfo.destinationId = destination("destinationId").as[String]
        destInfo.destinationType = destination("destinationType").as[String]
        destInfo.fileType = destination("fileType").as[String]
        destInfo.path = destination("path").as[String]
        destInfo.analyticalQuery = destination("analyticalQuery").as[String]

        println("Applying analytical transformations")
        val outputDf = spark.sqlContext.sql(destInfo.analyticalQuery)

        if (destInfo.destinationType == "hdfs") {
          destInfo.path = "hdfs://" + destInfo.path
        }

        if (destInfo.fileType == "csv") {
          outputDf.coalesce(1)
            .write
            .option("header", "true")
            .option("sep", ",")
            .mode("overwrite")
            .csv(destInfo.path)
        }
        else {
          outputDf.write.format(destInfo.fileType).save(destInfo.path)
        }

        println("Load Complete")

      }
    }

    catch {
      case ex: Throwable => println("An exception occurred while trying to run ETL" + ex.printStackTrace())
    }





  }

}
