package calculator.models

import com.fasterxml.jackson.databind.JsonNode
import scala.util.Try

case class Click(impressionId: String, revenue: Double) {
  def this(message: JsonNode) = {
    this(
      Try(message.get("impression_id").textValue()).getOrElse(""),
      Try(message.get("revenue").asDouble()).getOrElse(0.0)
    )
  }
}
