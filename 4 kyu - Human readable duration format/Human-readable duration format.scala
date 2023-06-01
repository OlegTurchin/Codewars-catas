object HumanTime {

  @scala.annotation.tailrec
  def format(seconds: Int, target: String = "",
             time: List[Int] = List(60, 3_600, 86_400, 31_536_000),
             units: List[String] = List("minute", "hour", "day", "year")): String = {
    if (seconds == 0) {
      if (target.endsWith(", ")) target.substring(0, target.length - 2)
      else "now"
    } else if (seconds < 60) (
      if (target.isBlank) target else target.substring(0, target.length - 2)
      ) + s"${
      if (target.isBlank) "" else " and "
    }$seconds ${
      if (seconds.toString.endsWith("1") && seconds.toString.length < 2) "second" else "seconds"
    }"
    else if (seconds / time.last > 0)
      format(seconds % time.last, target + s"${seconds / time.last} ${units.last}${
        if ((seconds / time.last).toString.endsWith("1") && (seconds / time.last).toString.length < 2) "" else "s"
      }, ", time.dropRight(1), units.dropRight(1))
    else format(seconds, target, time.dropRight(1), units.dropRight(1))
  }

  def formatDuration(seconds: Int): String = {
    val string : String = format(seconds)
    if (!string.contains("and") && string.contains(",")) string.patch(string.lastIndexOf(","), " and", 1) else string
  }
}