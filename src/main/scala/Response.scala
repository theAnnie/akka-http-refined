import spray.json.DefaultJsonProtocol

case class Response(kind: String, message: Option[String], index: Option[Int], radicand: Option[Int])

trait MyJsonProtocol extends DefaultJsonProtocol {
  implicit val responseFormat = jsonFormat4(Response)
}