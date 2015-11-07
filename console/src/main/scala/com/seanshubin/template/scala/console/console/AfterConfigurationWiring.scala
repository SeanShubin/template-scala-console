package com.seanshubin.template.scala.console.console

import com.seanshubin.template.scala.console.core._

trait AfterConfigurationWiring {
  def configuration: Configuration

  lazy val emitLine: String => Unit = println
  lazy val runner: Runnable = new RunnerImpl(configuration.greetingTarget, emitLine)
}
