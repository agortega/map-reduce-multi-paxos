name := "distributed-systems-cs171-proj3"

version := "1.0"

scalaVersion := "2.12.2"

val akkaVersion = "2.5.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7"

)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j"        % akkaVersion    % Test,
  "com.typesafe.akka" %%  "akka-testkit"     % akkaVersion    % Test,
  "org.scalatest"     %%  "scalatest"        % "3.0.1"        % Test,
  "ch.qos.logback"    %   "logback-classic"  % "1.1.7"        % Test,
  "com.typesafe.akka" %%  "akka-testkit"     % akkaVersion    % Test
)

// show elapsed time
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

//parallelExecution in Test := false
//addCommandAlias("node-1", "runMain src.main.scala.ClusterAwareRouterApp node-1")
//addCommandAlias("node-2", "runMain src.main.scala.ClusterAwareRouterApp node-2")


        