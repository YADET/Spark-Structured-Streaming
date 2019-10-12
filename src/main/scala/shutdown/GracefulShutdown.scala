package shutdown

import org.apache.log4j.Logger
import org.apache.spark.sql.streaming.StreamingQuery
import stream.SessionStarter

/** gracefully shutdown monitors the specified directory and shutdowns when a file created in that directory */
object GracefulShutdown extends SessionStarter{

  val logger = Logger.getLogger(getClass.getName)

  var stopFlag:Boolean = false

  def checkShutdownMarker = {
    if (!stopFlag) {
      stopFlag =  new java.io.File(cfg.getConfig("shutdownMarkerpath").getString("path")).exists()
    }

  }

  def gracefulShutdown(checkIntervalMillis:Int, streamingQueries: List[StreamingQuery]) {

    var isStopped = false

    while (!isStopped) {
      logger.info("calling awaitTerminationOrTimeout")
      isStopped = sparkSession.streams.awaitAnyTermination(checkIntervalMillis)
      if (isStopped)
        logger.info("confirmed! The streaming context is stopped. Exiting application...")
      else
        logger.info("Streaming App is still running. Timeout...")

      checkShutdownMarker

      if (!isStopped && stopFlag) {
        logger.info("stopping ssc right now")
        streamingQueries.map(query => {
          query.stop()
        })
        sparkSession.stop()
        logger.info("ssc is stopped!!!!!!!")

      }
    }
  }


}
