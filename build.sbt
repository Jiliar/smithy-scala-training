scalaVersion := "3.3.3"
version := "0.1.0"

val smithy4sVersion = "0.18.38"
val smithy4sEmberServerVersion = "0.23.30"

lazy val root = (project in file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    name := "smithy-training",
    scalaVersion := scalaVersion.value,
    Compile / mainClass := Some("smithy4sdemo.ServerMain"),
    Compile / run / fork := true,
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion,
      "org.http4s" %% "http4s-ember-server" % smithy4sEmberServerVersion,
      "org.http4s" %% "http4s-ember-client" % smithy4sEmberServerVersion,
      "org.typelevel" %% "cats-effect" % "3.5.2"
    ),
    fork := true, //This is needed to cancel the application gracefully avoiding problems with ports.
  )