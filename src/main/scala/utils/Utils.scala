package utils

/** User defined functions. */
object Utils {

  /** Creates a person with a given name and birthdate
   *
   *  @param p1 location 1
   *  @param p2 location 1
   *  @param t1 location 2
   *  @param t2 location 2
   *  @return distance between locations.
   */

  def getDistance(p1:Double, p2:Double, t1:Double, t2:Double):Double = {
    val r : Int = 6371 //Earth radius
    val latDistance : Double = Math.toRadians(t1 - p1)
    val lonDistance : Double = Math.toRadians(t2 - p2)
    val a : Double = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(p1)) * Math.cos(Math.toRadians(t1)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)
    val c : Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    val distance : Double = r * c
    distance
  }


}
