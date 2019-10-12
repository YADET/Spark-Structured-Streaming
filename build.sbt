name := "TestSpark"
 
version := "1.0"
scalaVersion := "2.12.6"

val kafkaVersion = "2.0.0"
val sparkVersion = "2.4.0"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
assemblyJarName in assembly := s"${name.value}_${scalaBinaryVersion.value}-${sparkVersion}_${version.value}.jar"


javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")

addCommandAlias("sanity", ";clean ;compile ;test ;coverage ; coverageReport")


lazy val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion
)

libraryDependencies ++= sparkDependencies.map(_ % "provided")
libraryDependencies ++= deps


dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.6.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.7"
dependencyOverrides += "org.scala-lang" % "scala-reflect" % "2.12.6"% "provided"

lazy val deps = { 
  Seq(
   "com.typesafe" % "config" % "1.3.3",
   "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
   "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
   "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "mysql" % "mysql-connector-java" % "8.0.11"

  )

}

lazy val LocalRunner = project.in(file("RunnerInLocal")).dependsOn(RootProject(file("."))).settings(
  scalaVersion := "2.12.6",
  libraryDependencies ++= sparkDependencies.map(_ % "compile")
).disablePlugins(sbtassembly.AssemblyPlugin)


run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

resolvers += "jitpack" at "https://jitpack.io"


assemblyExcludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter {
    i => i.data.getName == "slf4j-api-1.7.12.jar"
  }

}
