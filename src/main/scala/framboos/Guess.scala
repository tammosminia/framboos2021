package framboos

import scala.util.Random

class Guess()

object Guess {
  type Guess = String

  def randomLetter: Char = ('a' + Random.nextInt(26)).toChar
  def randomGuess(k: Knowledge): Guess = Seq.fill(k.numLetters)(randomLetter).mkString

  def guessWithCorrectLetters(k: Knowledge): Guess =
    k.correct.map { _.getOrElse(randomLetter)}.mkString

  def goodGuess(k: Knowledge): Guess = k match {
    case Knowledge(_, _, IndexedSeq()) => randomGuess(k)
    case _ => guessWithCorrectLetters(k)
  }


}
