organization := "com.typesafe.akka.samples"
name := "akka-sample-main-scala"

scalaVersion := "2.12.8"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.22"
)
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2"
licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
