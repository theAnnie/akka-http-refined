name := "scanye"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= {
  val akkaHttpV   = "10.1.10"
  val akkaStremsV = "2.5.26" //TODO: fix typo

  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-stream" % akkaStremsV
  )
}