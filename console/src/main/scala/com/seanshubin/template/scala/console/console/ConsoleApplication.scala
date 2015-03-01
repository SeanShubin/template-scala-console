package com.seanshubin.template.scala.console.console

object ConsoleApplication extends App with LauncherWiring {
  override def commandLineArguments: Seq[String] = args

  launcher.launch()
}
