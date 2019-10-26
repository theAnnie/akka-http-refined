import spray.json.DefaultJsonProtocol

final case class Message(jarUri: String, className: String, args: List[String])

trait ApplicationProtocol extends DefaultJsonProtocol {
  implicit val appFormat = jsonFormat3(Message)
}