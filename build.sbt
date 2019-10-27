name := "scanye"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= {
  val akkaHttpV = "10.1.10"
  val akkaStreamsV = "2.5.26"
  val refinedV = "0.9.10"
  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-stream" % akkaStreamsV,
    "eu.timepit" %% "refined" % refinedV
  )
}