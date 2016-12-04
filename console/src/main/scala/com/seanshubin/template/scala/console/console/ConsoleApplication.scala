package com.seanshubin.template.scala.console.console

object ConsoleApplication extends App {
  new DependencyInjection {
    override def commandLineArguments: Seq[String] = args
  }.runner.run()
}
