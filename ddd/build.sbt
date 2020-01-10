name := "ddd"

version := "0.1"

scalaVersion := "2.13.1"

val ScalaTest = "3.1.0"

libraryDependencies ++= Seq(
  "org.scalacheck"  %%  "scalacheck"  %   "1.14.1"    %   "test",
  "org.scalactic"    %% "scalactic"      % ScalaTest,
  "org.scalatest"    %% "scalatest"      % ScalaTest % Test,
)