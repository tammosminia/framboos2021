package framboos


import zio._
import sttp.client.asynchttpclient.zio._
import zio.console.Console


object Main extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    loop(Knowledge.No).provideCustomLayer(AsyncHttpClientZioBackend.layer())
      .exitCode

  def loop(k: Knowledge): ZIO[Console with SttpClient, Throwable, Knowledge] = for {
//    newK <- runOne(k)
    newK <- runMultiple(10, k).orElse(UIO(Knowledge.No))
    l <- loop(Knowledge.merge(k, newK))
  } yield l

  def runMultiple(n: Int, k: Knowledge): ZIO[Console with SttpClient, Throwable, Knowledge] = for {
    newKs <- ZIO.foreachPar(List.fill(n)(k))(runOne)
  } yield newKs.fold(k)(Knowledge.merge)

  def runOne(k: Knowledge): ZIO[Console with SttpClient, Throwable, Knowledge] = for {
    guess <- Task(Guess.goodGuess(k))
    request = ApiClient.SolutionRequest(k.round, guess)
    response <- ApiClient.send(request)
    newK = Knowledge.fromResponse(response, guess)
    _  <- console.putStrLn(s"$guess old:$k new:$newK $response")
  } yield newK

}
