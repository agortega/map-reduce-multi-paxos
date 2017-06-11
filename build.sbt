name := "distributed-systems-cs171-proj3"

version := "1.0"

scalaVersion := "2.11.2"

autoCompilerPlugins := true


addCompilerPlugin("org.scala-lang.plugins" % "scala-continuations-plugin_2.11.2" % "1.0.2")

libraryDependencies += "org.scala-lang.plugins" % "scala-continuations-library_2.11" % "1.0.2"

scalacOptions += "-P:continuations:enable"

val akkaVersion = "2.4.1"


libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % akkaVersion

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster" % "2.5.2",
  "com.typesafe.akka" %% "akka-remote" % "2.5.2"
)




resolvers ++= Seq(
  "Sonatype OSS Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at
    "https://oss.sonatype.org/content/repositories/releases"
)
libraryDependencies ++= Seq(
  "io.reactors" %% "reactors" % "0.8")



