name := "distributed-systems-cs171-proj3"

version := "1.0"

scalaVersion := "2.11.2"

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "scala-continuations-plugin_2.11.2" % "1.0.2")

libraryDependencies += "org.scala-lang.plugins" % "scala-continuations-library_2.11" % "1.0.2"

scalacOptions += "-P:continuations:enable"