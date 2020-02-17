val dottyVersion = "0.23.0-bin-20200214-5374d91-NIGHTLY"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-workshop",
    version := "0.1.0",
    scalaVersion := dottyVersion,
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test,
    testFrameworks += TestFrameworks.JUnit,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked"
    )
  )
