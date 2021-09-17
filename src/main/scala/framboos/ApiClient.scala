package framboos

import zio._
import sttp.client._
import sttp.client.asynchttpclient.zio._
import sttp.client.json4s._
import zio.console.Console

object ApiClient {
  val endpoint = uri"https://vrolijkeframboos.herokuapp.com/solution"
  val user = "Tammo"
  val password = "Tammo"

  case class SolutionRequest(round: Long, solution: String)
  case class SolutionResponse(result: String, length: Int, currentRound: Long, problem: String)
  implicit val serialization = org.json4s.native.Serialization
  implicit val formats = org.json4s.DefaultFormats

  def send(sr: SolutionRequest): ZIO[Console with SttpClient, Throwable, SolutionResponse] = {
    val request = basicRequest
      .auth.basic(user, password)
      .body(sr)
      .post(endpoint)
      .response(asJson[SolutionResponse])
    for {
      response <- SttpClient.send(request)
      _  <- console.putStrLn(response.toString)
      body <- ZIO.fromEither(response.body)
    } yield body
  }

}
