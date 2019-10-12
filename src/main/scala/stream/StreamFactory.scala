package stream



object StreamFactory {

  /** instantialte the spark job. */

  def factory(): Unit ={
    val stream=new StructutedStreaming()
    stream.taskone()
  }

}
