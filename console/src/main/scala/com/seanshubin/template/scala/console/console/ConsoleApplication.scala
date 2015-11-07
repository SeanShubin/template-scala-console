package com.seanshubin.template.scala.console.console

object ConsoleApplication extends App {
  new TopLevelWiring {
    override def commandLineArguments: Seq[String] = args
  }.runner.run()
}
