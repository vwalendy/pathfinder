ThisBuild / scalaVersion := "3.7.2"

lazy val sharedSettings = Seq(
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),
)
lazy val jsSettings = sharedSettings ++ Seq(
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.1",
)

lazy val pathfinder = project.in(file("."))
  .settings(
    name := "pathfinder",
    sharedSettings,
    libraryDependencies += "org.scalameta" %% "munit" % "1.1.1" % Test,
  )
  .aggregate(cross.jvm)

lazy val cross = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(
    sharedSettings,
    Test / skip := true,
    Test / compile / skip := true,
    Test / test := {},
    Test / testOnly := {},
  )

lazy val bloxorz = project.in(file("bloxorz-www"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    jsSettings,
    scalaJSUseMainModuleInitializer := true,
    Test / skip := true,
    Test / compile / skip := true,
    Test / test := {},
    Test / testOnly := {},
  )
  .dependsOn(cross.js)

addCommandAlias("buildBloxorz", "bloxorz/compile;bloxorz/fastLinkJS;")
addCommandAlias("testSenior", "testOnly pathfinder.SolverSystemSuite")
