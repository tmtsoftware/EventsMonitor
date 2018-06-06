name := "events-monitor"
version := "0.1"
scalaVersion := "2.12.6"

lazy val `events-monitor-api` = project
  .settings(
    libraryDependencies ++= Seq(
      Circe.`circe-core`.value,
      Circe.`circe-generic`.value,
      Circe.`circe-parser`.value,
    )
  )

lazy val `events-monitor-client` = project
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`events-monitor-api`)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    libraryDependencies ++= Seq(
      Libs.`scala-js-dom`.value,
      Libs.`scalatest`.value % Test,
    )
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
      Libs.`scalatest`.value % Test,
      Libs.`mockito-core` % Test,
      Akka.`akka-typed-testkit` % Test,
    )
  )
