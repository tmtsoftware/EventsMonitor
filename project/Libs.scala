import sbt._
import Def.{setting => dep}
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

object Akka {
  val Version = "2.5.11"

  val `akka-stream`        = "com.typesafe.akka" %% "akka-stream"        % Version
  val `akka-typed`         = "com.typesafe.akka" %% "akka-actor-typed"   % Version
  val `akka-http`          = "com.typesafe.akka" %% "akka-http"          % "10.1.1"
  val `akka-http-tetkit`   = "com.typesafe.akka" %% "akka-http-testkit"  % "10.1.1"
  val `akka-typed-testkit` = "com.typesafe.akka" %% "akka-testkit-typed" % Version
  val `akka-http-circe`    = "de.heikoseeberger" %% "akka-http-circe"    % "1.20.1" //Apache 2.0
}

object Csw {
  val `csw-event-client` = "org.tmt" %% "csw-event-impl" % "0.1-SNAPSHOT"
}

object Libs {
  val `akka-http-cors` = "ch.megard" %% "akka-http-cors" % "0.3.0"
  val `scalatest`      = dep("org.scalatest" %%% "scalatest" % "3.0.5") //Apache License 2.0
  val `scala-js-dom`   = dep("org.scala-js" %%% "scalajs-dom" % "0.9.6") //Apache License 2.0
  val `mockito-core`   = "org.mockito" % "mockito-core" % "2.16.0" //MIT License
}

object Covenant {
  val Version = "271d249"

  val `covenant-http` = dep("com.github.cornerman.covenant" %%% "covenant-http" % Version)
  val `covenant-ws`   = dep("com.github.cornerman.covenant" %%% "covenant-ws"   % Version)
}

object Circe {
  val Version = "0.9.3"

  val `circe-core`    = dep("io.circe" %%% "circe-core"    % Version)
  val `circe-generic` = dep("io.circe" %%% "circe-generic" % Version)
  val `circe-parser`  = dep("io.circe" %%% "circe-parser"  % Version)
}
