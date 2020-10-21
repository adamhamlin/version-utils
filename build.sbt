
name := "VersionUtils"

organization := "adamhamlin"

version := "0.1"

scalaVersion := "2.12.10"

// The dependencies are in Maven format, with % separating the parts.
// Notice the extra bit "test" on the end of ScalaTest, which will
// mean it is only a test dependency.
//
// The %% means that it will automatically add the specific Scala version to the dependency name.
// For instance, this will actually download scalatest_2.12.10

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

// Initial commands to be run in your REPL.  I like to import various project-specific things here.

initialCommands := """ """