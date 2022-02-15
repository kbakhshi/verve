package calculator.tasks

import org.apache.log4j.Logger
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.SparkConf
import scala.collection.JavaConverters._


abstract class BaseTask(val taskName: String) {
  val logger: Logger = Logger.getLogger(getClass)
  val appConf: Config = ConfigFactory.load
  val taskConf: Config = ConfigFactory.parseResources("config/" + taskName + ".conf").withFallback(appConf)

  def processData(): Unit

  def performTask(): Unit = {
    try {
      processData()
    }
    catch {
      case exception: Throwable =>
        logger.warn(exception)
        throw exception
    }
  }


}

