
scalaVersion in ThisBuild := "2.12.2"
lazy val commonSettings = Seq(
  organization := "io.angelortega",
  version := "0.1.0-SNAPSHOT"
)

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "fat-jar-test"
  ).
  enablePlugins(AssemblyPlugin)

resolvers in Global ++= Seq(
  "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
)
val akkaVersion = "2.5.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
)

enablePlugins(AssemblyPlugin)

//assemblyJarName in assembly := "something.jar"
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-slf4j"        % akkaVersion    % Test,
//  "com.typesafe.akka" %%  "akka-testkit"     % akkaVersion    % Test,
//  "org.scalatest"     %%  "scalatest"        % "3.0.1"        % Test,
//  "ch.qos.logback"    %   "logback-classic"  % "1.1.7"        % Test,
//  "com.typesafe.akka" %%  "akka-testkit"     % akkaVersion    % Test
//)

// show elapsed time
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

//parallelExecution in Test := false
//addCommandAlias("node-1", "runMain src.main.scala.ClusterAwareRouterApp node-1")
//addCommandAlias("node-2", "runMain src.main.scala.ClusterAwareRouterApp node-2")

mainClass in Compile := Some("cli.Main")
