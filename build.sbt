lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion    = "2.6.5"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.github.dwiechert",
      scalaVersion    := "2.13.1",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "cicd-series",

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",

      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.0.8"         % Test
    ),

    mainClass in assembly := Some("com.github.dwiechert.Main"),

    // Ignore tests when running the "assembly" task
    test in assembly := {}
  )
