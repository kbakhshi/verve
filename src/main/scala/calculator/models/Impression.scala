package calculator.models

import com.fasterxml.jackson.databind.JsonNode
import scala.util.Try

case class Impression(appId: Long, advertiserId: Long, countryCode: String, id: String) {
  def this(message: JsonNode) = {
    this(
      Try(message.get("app_id").asLong()).getOrElse(-1),
      Try(message.get("advertiser_id").asLong()).getOrElse(-1),
      Try(message.get("country_code").textValue()).getOrElse(""),
      Try(message.get("id").textValue()).getOrElse("")

    )
  }
}

