import sbt.Keys.libraryDependencies

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
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(JavaAgent)
  .settings(
    reStart / javaOptions ++= (run / javaOptions).value,
    javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.7" % "compile;test;dist",
    libraryDependencies ++= Seq(
      AkkaHttp.`akka-http`,
      AkkaHttp.`akka-http-tetkit`,
      AkkaHttp.`akka-http2-support`,
      Libs.`akka-http-circe`,
      Libs.`akka-http-cors`,
      Csw.`csw-event-client`,
      Libs.`scalatest`.value % Test,
      Libs.`mockito-core` % Test,
      Akka.`akka-actor-testkit-typed` % Test,
    ),
    mappings in Universal ++= {
      val scriptsDirectory = baseDirectory.value.getParentFile /  "pages"
      scriptsDirectory.allPaths pair Path.relativeTo(scriptsDirectory) map {
        case (file, relativePath) => file -> s"pages/$relativePath"
      }
    },
    resourceGenerators in Compile += Def.task {
      Seq((fastOptJS in Compile in `events-monitor-client`).value.data)
    }.taskValue,
    watchSources ++= (watchSources in `events-monitor-client`).value
  )

lazy val `events-monitor-ui` = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    resolvers += Resolver.sonatypeRepo("snapshots"),
    scalaJSUseMainModuleInitializer := true,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    npmDependencies in Compile ++= Seq(
      "react" -> "16.2.0",
      "react-dom" -> "16.2.0"),
    libraryDependencies ++= Seq(
      React4s.`react4s`.value,
      React4s.`react4s-router`.value,
      Circe.`circe-core`.value,
      Circe.`circe-generic`.value,
      Circe.`circe-generic-extras`.value,
      Circe.`circe-parser`.value
    )
  )
