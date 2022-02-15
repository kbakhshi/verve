package calculator.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object Mapper {
  val objectMapper: ObjectMapper = new ObjectMapper()
  objectMapper.registerModule(DefaultScalaModule)

}
