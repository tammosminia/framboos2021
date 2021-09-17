name := "framboos-2021"

version := "0.1"

scalaVersion := "2.13.3"

assemblyJarName in assembly := "framboos-fatjar-1.0.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "dev.zio"                      %% "zio"                           % "1.0.1",
  "dev.zio"                      %% "zio-streams"                   % "1.0.1",
  "com.softwaremill.sttp.client" %% "core"                          % "2.2.8",
  "com.softwaremill.sttp.client" %% "async-http-client-backend-zio" % "2.2.8",
  "com.softwaremill.sttp.client" %% "json4s" % "2.2.8",
  "org.json4s" %% "json4s-native" % "3.6.9",

)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
