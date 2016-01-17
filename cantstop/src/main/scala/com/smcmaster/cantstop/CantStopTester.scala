import scala.collection.mutable.Map

/**
 * Simulates all possible triples of numbers in the game
 * Can't Stop (https://boardgamegeek.com/boardgame/41/cant-stop).
 * Outputs the average number of rolls you can go on each
 * triple without losing.
 */
object CantStopTester extends App {
  val dice = new Dice
  
  val trials = 10000
  val numbers = for (i <- 2 to 12) yield i
  val result = Map[String, Float]()
  for (markers <- numbers.combinations(3)) {
    var totalRolls = 0
    for (i <- 1 to trials) {
      var stillPlaying = true
      while (stillPlaying) {
        stillPlaying = false
        val roll = dice.roll()
        for (marker <- markers) {
          if (!stillPlaying && roll.contains(marker)) {
            stillPlaying = true
            totalRolls = totalRolls + 1
          }
        }
      }
    }
    val avg = totalRolls.toFloat / trials
    result.put(markers mkString "|", avg)
  }
  val sortedResults = result.toSeq.sortBy(_._1)
  for (entry <- sortedResults) {
    println(entry._1 + ": " + entry._2)
  }
}