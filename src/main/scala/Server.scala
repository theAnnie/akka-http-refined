import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer

import scala.io.StdIn

object Server extends App with Directives {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  private val host = "localhost"
  private val port = 8080

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(POST,Uri.Path(_),_,_@entity,_) =>
      print(entity)
      HttpResponse(200, entity = "Gitara siema")

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