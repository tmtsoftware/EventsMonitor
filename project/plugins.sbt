addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "0.6.23")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.4.0")
addSbtPlugin("ch.epfl.scala"      % "sbt-web-scalajs-bundler"  % "0.12.0")
addSbtPlugin("io.get-coursier"    % "sbt-coursier"             % "1.0.2")
addSbtPlugin("io.spray"           % "sbt-revolver"             % "0.9.1")

classpathTypes += "maven-plugin"
