package kafka

import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import schema.NestedSchema._

object KafkaSource{

  val logger = Logger.getLogger(getClass.getName)
  val cfg = ConfigFactory.load("config.conf")

  def readStream()(implicit sparkSession: SparkSession) = {

    logger.info("Reading from Kafka")

    /** reads data from kafka topic. */
    sparkSession
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "topic1")
      .option("startingOffsets", "latest")
      .load()
      .select(from_json(col("value").cast("string"), nschema, jsonOptions).alias("parsed_value"))


  }

}
