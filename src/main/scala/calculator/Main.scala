package calculator

import org.apache.log4j.Logger
import calculator.tasks.{PartTwo, PartThree}

object Main {
  val logger: Logger = Logger.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    val task = args(0) match {
      case "PartTwo" => new PartTwo()
      case "PartThree" => new PartThree()
    }
    task.performTask()
  }
}
