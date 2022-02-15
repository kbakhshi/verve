package calculator.utils

import com.typesafe.config.Config
import calculator.models.{Click, Impression}
import com.fasterxml.jackson.databind.JsonNode
import scala.language.postfixOps

class ReadData(config: Config) {

  def readImpressionData(): List[Impression] = {
    val impressionPath = config.getString("input_path.impressions_path")
    val lines = scala.io.Source.fromFile(impressionPath).mkString
    val listOfImpressionJsonNode = Mapper.objectMapper.readValue(lines, classOf[JsonNode])
    var result: List[Impression] = List.empty
    import scala.collection.JavaConversions._
    for (impressionJsonNode <- listOfImpressionJsonNode) {
      result = new Impression(impressionJsonNode) :: result
    }
    result
  }

  def readClickData(): List[Click] = {
    val clicksPath = getClass.getClassLoader.getResource(config.getString("input_path.clicks_path")).getPath
    val lines = scala.io.Source.fromFile(clicksPath).mkString
    val listOfImpressionJsonNode = Mapper.objectMapper.readValue(lines, classOf[JsonNode])
    var result: List[Click] = List.empty
    import scala.collection.JavaConversions._
    for (impressionJsonNode <- listOfImpressionJsonNode) {
      result = new Click(impressionJsonNode) :: result
    }
    result
  }
}

