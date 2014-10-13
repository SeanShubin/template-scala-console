package com.seanshubin.template.scala.console.console

import com.seanshubin.template.scala.console.core.{Launcher, LauncherImpl, Runner}

trait ProductionLauncherWiring {
  def commandLineArguments: Seq[String]

  lazy val createRunner: String => Runner =
    target => ProductionRunnerWiring(target).runner

  lazy val launcher: Launcher = new LauncherImpl(commandLineArguments, createRunner)

}

object ProductionLauncherWiring {
  def apply(theCommandLineArguments: Seq[String]) = new ProductionLauncherWiring {
    override def commandLineArguments: Seq[String] = theCommandLineArguments
  }
}
