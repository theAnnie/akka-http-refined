import akka.http.scaladsl.model.HttpResponse
import spray.json.JsValue

import scala.util.{Failure, Success, Try}

object ResponseHandler extends StaticValues {

  def handleResponse(response: Try[JsValue]): HttpResponse = {

    response match {
      case Failure(_) => errorResponse
      case Success(x) => matchResponse(x)
    }
  }

  private def matchResponse(maybeResponse: JsValue): HttpResponse = {
    val fields: Map[String, JsValue] = maybeResponse.asJsObject.fields

    fields.get("kind") match {
      case Some(x) if x.toString() == "\"ping\"" => HttpResponse(204)
      case Some(x) if x.toString() == "\"echo\"" => HttpResponse(200, entity = s"${fields.getOrElse("message", "")}")
      case Some(x) if x.toString() == "\"ping\"" => errorResponse
      case _ => errorResponse
    }

  }
}