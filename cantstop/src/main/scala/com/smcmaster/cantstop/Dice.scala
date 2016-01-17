import scala.util.Random

/**
 * Simulates a roll of three dice.
 */
class Dice {
  val rand = new Random
  
  def roll() : Set[Integer] = {
    val dice = List(rand.nextInt(6) + 1, rand.nextInt(6) + 1,
        rand.nextInt(6) + 1, rand.nextInt(6) + 1)
    var result = Set[Integer]()
    for (combo <- dice.combinations(2)) {
      val total = combo(0) + combo(1)
      result = result + total
    }
    result
  }
}