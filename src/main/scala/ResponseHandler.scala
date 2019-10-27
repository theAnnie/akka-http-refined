import akka.http.scaladsl.model.HttpResponse
import eu.timepit.refined.numeric.{GreaterEqual, Positive}
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.{W, refineV}

import scala.util.{Failure, Success, Try}

object ResponseHandler extends StaticValues {

  def handle(maybeResponse: Try[Response]): HttpResponse = {
    maybeResponse match {
      case Failure(_) => errorResponse
      case Success(x) => matchResponse(x)
    }
  }

  private def matchResponse(response: Response): HttpResponse = {
    response match {
      //@formatter:off
      case Response("ping", _, _, _)                                   => HttpResponse(204)
      case Response("echo", message, _, _) if message.isDefined        => handleEcho(message.get)
      case Response("realRoot", _, i, r) if i.isDefined && r.isDefined => handleRealRoot(i.get, r.get)
      case _                                                           => errorResponse
      //@formatter:on
    }
  }

  private def handleEcho(message: String): HttpResponse = {
    refineV[MatchesRegex[W.`"[a-zA-Z]+"`.T]](message) match {
      case Left(_) => errorResponse
      case Right(msg) => HttpResponse(200, entity = msg.toString())
    }
  }

  private def handleRealRoot(maybeIndex: Int, maybeRadicand: Int): HttpResponse = {
    val index = refineV[Positive](maybeIndex)
    val radicand = refineV[GreaterEqual[0]](maybeRadicand)

    val maybeResult = for {
      i <- index
      r <- radicand
    } yield i.value + r.value

    maybeResult match {
      case Left(_) => errorResponse
      case Right(x) => HttpResponse(200, entity = "{\"realRoot\": " + x + "}")
    }
  }
}