name := "verve"
version := "0.1"
scalaVersion := "2.12.10"

libraryDependencies   ++= Seq(
  "com.typesafe" % "config" % "1.4.0",
  // Test Scope
  "org.scalatest"     %% "scalatest"            % "3.1.1"       % "test",
  "org.scalamock"    %% "scalamock"            % "4.4.0"       % "test"
)
libraryDependencies += "org.apache.spark" %% "spark-sql"            % "3.2.0"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}
assemblyOutputPath in assembly := new File(baseDirectory.value, "verve.jar")
