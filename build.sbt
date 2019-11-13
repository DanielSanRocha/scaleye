name := "scaleye"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.0"

run / fork := true

lazy val global = project.in(file(".")).aggregate(lib, examples)

lazy val lib = project
  .in(file("lib"))
  .settings(name := "lib")
  .settings(libraryDependencies ++= libDependencies)
  .settings(assemblyJarName in assembly := "scaleye.jar")

lazy val examples = project
  .in(file("examples"))
  .settings(name := "examples")
  .settings(libraryDependencies ++= libDependencies)
  .settings(assemblyJarName in assembly := "examples.jar")
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
  "ch.qos.logback" % "logback-classic" % logbackVersion
)

// Publish configurations

global / publish / skip := true
examples / publish / skip := true

publishTo := Some(Resolver.file("local-ivy", file("path/to/ivy-repo/releases")))
