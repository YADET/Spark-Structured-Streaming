package utiltest

import org.scalatest.time._
import utils.Utils
import commons._

class MethodTests extends TestHelper{


  it should "calculate the distance of two geo_location" in withSparkSession  { spark =>

  val df = spark.read.json(cfg.getConfig("sampledata").getString("sd1"))
  val lon1=df.select("p1","p2","t1","t2").collect().toList
  val cols=lon1(0)

    eventually (timeout(Span(1, Seconds))) {
      val t1= Utils.getDistance(cols(0).toString.toDouble,cols(1).toString.toDouble, cols(2).toString.toDouble,cols(3).toString.toDouble)
      f"$t1%1.5f".toDouble should be (0.06808)
      }

    }


}
