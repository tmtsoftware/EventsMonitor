name := "events-monitor"
version := "0.1"
scalaVersion := "2.12.6"

lazy val `events-monitor-api` = project
  .settings(
    libraryDependencies ++= Seq(
      Akka.`akka-stream`,
      Circe.`circe-core`.value,
      Circe.`circe-generic`.value,
      Circe.`circe-parser`.value,
    )
  )

lazy val `events-monitor-client` = project
  .dependsOn(`events-monitor-api`)
  .settings(
  )

lazy val `events-monitor-server` = project
  .dependsOn(`events-monitor-api`)
  .settings(
    libraryDependencies ++= Seq(
      Akka.`akka-http`,
      Akka.`akka-http-tetkit`,
      Akka.`akka-http-circe`,
      Libs.`akka-http-cors`,
      Csw.`csw-event-client`,
      Libs.`scalatest` % Test,
      Libs.`mockito-core` % Test,
      Akka.`akka-typed-testkit` % Test,
    )
  )

lazy val `covenant-streaming` = project
  .settings(
    name := "sequencer-framework",
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= Seq(
      Circe.`circe-core`.value,
      Circe.`circe-generic`.value,
      Circe.`circe-parser`.value,
      Covenant.`covenant-http`.value,
      Covenant.`covenant-ws`.value,
      Libs.`akka-http-cors`,
    )
  )
