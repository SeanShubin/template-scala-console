package com.seanshubin.template.scala.console.core

import java.time.Duration

abstract class NotificationsNotImplemented extends Notifications {
  override def effectiveConfiguration(configuration: Configuration): Unit = ???

  override def timeTaken(duration: Duration): Unit = ???

  override def configurationError(lines: Seq[String]): Unit = ???

  override def topLevelException(exception: Throwable): Unit = ???
}