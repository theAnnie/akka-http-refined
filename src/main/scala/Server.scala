import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import spray.json.JsValue

import scala.concurrent.Future
import scala.io.StdIn

object Server extends App with Directives with StaticValues {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  private val host = "localhost"
  private val port = 8080

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(POST, Uri.Path(_), _, _@entity, _) =>

      val x: Future[JsValue] = Unmarshal(entity).to[JsValue]

      x.value match {
        case Some(y) => ResponseHandler.handle(y)
        case None => errorResponse

      }

    case other: HttpRequest =>
      other.discardEntityBytes()
      HttpResponse(404, entity = "404 not found")
  }

  val bindingFuture = Http().bindAndHandleSync(requestHandler, host, port)

  println("Server ready")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}