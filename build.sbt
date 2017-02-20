name := "docs_example_api"

version := "0.0.1"

lazy val `docs_example_api` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
  "org.postgresql" % "postgresql" % "9.4.1212",
  // "com.h2database" % "h2" % "1.4.193" % "test",
  specs2 % Test
)

