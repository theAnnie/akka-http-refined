import akka.http.scaladsl.model.HttpResponse

trait StaticValues {
  protected final val errorResponse = HttpResponse(404, entity = "404 not found")
}
