package stream

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

trait SessionStarter {

  /** gets the configuration files. */

  val cfg = ConfigFactory.load("config.conf")

  /** Create an implicit spark session. */

  implicit val sparkSession = SparkSession
    .builder
    .appName("TestSpark")
    .config("spark.streaming.stopGracefullyOnShutdown", cfg.getConfig("shutdownMarkerpath").getString("path"))
    .config("spark.sql.streaming.checkpointLocation", cfg.getConfig("checkpointpath").getString("path"))
    .config("spark.streaming.receiver.writeAheadLog.enable",cfg.getConfig("walenabling").getString("en"))
    .config("spark.streaming.backpressure.enabled",cfg.getConfig("backpressure").getString("en"))
    .config("spark.driver.memory", "3g")
    .config("spark.scheduler.poll", "fair_poll")
    .config("spark.cores.max", '1')
    .config("spark.scheduler.mode", "FAIR")
    .master(cfg.getConfig("manager").getString("master"))
    .getOrCreate()

}
