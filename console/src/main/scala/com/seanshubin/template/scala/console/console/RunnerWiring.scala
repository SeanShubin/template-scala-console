package com.seanshubin.template.scala.console.console

import com.seanshubin.template.scala.console.core._

trait RunnerWiring {
  def configuration: Configuration

  lazy val emitLine: String => Unit = println
  lazy val runner: Runner = new RunnerImpl(configuration.greetingTarget, emitLine)
}
