//project settings
name := "intro-cats"
organization := "co.edu.eafit.dis.progfun"
version := "0.1.0"
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core"   % "2.1.0",
  "org.typelevel" %% "cats-effect" % "2.0.0"
)
