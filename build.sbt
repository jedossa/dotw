val dottyVersion = "0.20.0-bin-20191021-0bf296e-NIGHTLY"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-workshop",
    version := "0.1.0",
    scalaVersion := dottyVersion,
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test,
    testFrameworks += TestFrameworks.JUnit
  )
