//project settings
val appName = "ProyectoEjemplo"
name := appName
version := "0.1.0"
scalaVersion := "2.12.6"
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "utf-8",
  "explaintypes",
  "-feature",
  "-unchecked",
  "-Ypartial-unification",
)
scalacOptions in (doc) ++= Seq("-groups", "-implicits")
scalaOptions in (Compile, doc) ++= Seq