package framboos

case class Knowledge(round: Long, numLetters: Int, correct: IndexedSeq[Option[Char]])//, wrongPlace: List[Char] = )

object Knowledge {
  val No = Knowledge(0, 5, Vector.empty)

  def merge(o: Knowledge, n: Knowledge): Knowledge = {
//    val (o, n) = if (a.round > b.round) (b, a) else (a, b)
    val correct = if (o.round != n.round || o.numLetters != n.numLetters) n.correct
      else o.correct.zipAll(n.correct, None, None).map { case (o, n) => n.orElse(o) }
    Knowledge(n.round, n.numLetters, correct)
  }

  def fromResponse(r: ApiClient.SolutionResponse, guess: Guess.Guess): Knowledge = {
    val correct = r.problem.zip(guess.padTo(r.length, '.')).map {
      case (_, '.') => None
      case ('.', _) => None
      case ('*', _) => None
      case ('^', l) => Some(l)
      case (l, g) => Some(l)
    }
    Knowledge(r.currentRound, r.length, correct)
  }


}

