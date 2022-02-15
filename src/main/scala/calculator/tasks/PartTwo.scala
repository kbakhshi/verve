package calculator.tasks

import java.io.{File, PrintWriter}

import calculator.utils.{Mapper, ReadData}


class PartTwo extends BaseTask("PartTwo") {

  def processData(): Unit = {

    val impressionData = new ReadData(taskConf).readImpressionData()
    val clickData = new ReadData(taskConf).readClickData()

    val res = impressionData
      .filter(imp => imp.countryCode != "" && imp.appId != -1 && imp.id != "")
      .groupBy(data => (data.appId, data.countryCode))
      .map(r =>
        Mapper.objectMapper.writeValueAsString(
          Map(
            "app_id" -> r._1._1,
            "country_code" -> r._1._2,
            "impressions" -> r._2.length,
            "clicks" -> r._2.distinct.map(imp => clickData.filter(c => c.impressionId == imp.id).length).sum,
            "revenue" -> r._2.distinct.map(imp => clickData.filter(c => c.impressionId == imp.id).map(s => s.revenue).sum).sum
          )
        ))

    val writer = new PrintWriter(new File(taskConf.getString("output_path")))
    writer.write(res.toString())
    writer.close()
  }

}
