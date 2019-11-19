name := "scaleye"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.12.8"

run / fork := true

lazy val global = project.in(file(".")).settings(noPublish).aggregate(lib, examples)

lazy val lib = project
  .in(file("lib"))
  .settings(moduleName := "scaleye")
  .settings(libraryDependencies ++= libDependencies)
  .settings(assemblyJarName in assembly := "scaleye.jar")
  .settings(publishSettings)

lazy val examples = project
  .in(file("examples"))
  .settings(name := "examples")
  .settings(libraryDependencies ++= libDependencies)
  .settings(assemblyJarName in assembly := "examples.jar")
  .settings(noPublish)
  .dependsOn(lib)

lazy val javacvVersion = "1.4"
lazy val javacppVersion = "1.4"
lazy val opencvVersion = "3.4.0"

lazy val typesafeConfigVersion = "1.3.1"
lazy val scalaLoggingVersion = "3.9.2"
lazy val logbackVersion = "1.2.3"

lazy val libDependencies = Seq(
  "org.bytedeco" % "javacv-platform" % javacvVersion % "provided",
  "com.typesafe" % "config" % typesafeConfigVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

// Publish configurations
publishArtifact in lib := true
publishArtifact in examples := false

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publish / skip := true
)

lazy val publishSettings = Seq(
  organization := "com.danielsanrocha",
  homepage := Some(url("https://github.com/danielsanrocha/scaleye")),
  scmInfo := Some(ScmInfo(url("https://github.com/danielsanrocha/scaleye"), "git@github.com:danielsanrocha/scaleye.git")),
  developers := List(
    Developer("danielsanrocha", "Daniel Santana Rocha", "danielsantanarocha@gmail.com", url("https://github.com/danielsanrocha"))
  ),
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  publishMavenStyle := true,
  publishArtifact := true,
  publishArtifact in Test := false,
  publishTo := Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  )
)
