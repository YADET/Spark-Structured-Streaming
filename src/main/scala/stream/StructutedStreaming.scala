package stream

import dao.JdbcSink
import kafka.KafkaSource
import org.apache.log4j.Logger
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{from_json, _}
import shutdown.GracefulShutdown
import utils.Utils._
import org.apache.spark.sql.streaming.{OutputMode, Trigger}

class StructutedStreaming extends SessionStarter {

  val logger = Logger.getLogger(getClass.getName)


    def taskone() {

      import sparkSession.implicits._

      val gate_distance: UserDefinedFunction = udf(getDistance _)

      /** Read kafka stream. */
      val rawStream = KafkaSource.readStream()

      /** explode nested Maptypes. */
      val camera = rawStream
        .select(explode($"parsed_value.devices.cameras"))
        .select("value.*")

      /** select the required fields. */
      val sightings = camera
        .select("device_id", "last_event.has_person", "last_event.has_sound")

      val console = sightings
        .writeStream
        .format("console")
        .outputMode(OutputMode.Append())

      val query = console.start()

      /** sink to mysql database. */
      val writer = new JdbcSink(cfg.getConfig("db").getString("url"),
        cfg.getConfig("db").getString("user"),
        cfg.getConfig("db").getString("pwd"))
      val query2 =
        sightings
          .writeStream
          .foreach(writer)
          .outputMode("update")
          .trigger(Trigger.ProcessingTime("2 seconds"))
          .start()

      GracefulShutdown.gracefulShutdown(1000, List(query,query2))

  }



}
