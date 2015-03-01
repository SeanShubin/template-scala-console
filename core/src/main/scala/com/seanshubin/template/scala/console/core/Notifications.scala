package com.seanshubin.template.scala.console.core

trait Notifications {
  def effectiveConfiguration(configuration: Configuration)

  def configurationError(lines: Seq[String])

  def topLevelException(exception: Throwable)
}
