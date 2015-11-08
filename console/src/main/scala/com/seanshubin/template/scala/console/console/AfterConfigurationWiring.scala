package com.seanshubin.template.scala.console.console

import java.time.Clock

import com.seanshubin.devon.core.devon.{DevonMarshaller, DevonMarshallerWiring}
import com.seanshubin.template.scala.console.core._

trait AfterConfigurationWiring {
  def configuration: Configuration

  lazy val emitLine: String => Unit = println
  lazy val clock: Clock = Clock.systemUTC()
  lazy val devonMarshaller: DevonMarshaller = DevonMarshallerWiring.Default
  lazy val notifications: Notifications = new LineEmittingNotifications(devonMarshaller, emitLine)
  lazy val timer: Timer = new TimerImpl(clock)
  lazy val runner: Runnable = new RunnerImpl(configuration.greetingTarget, emitLine, timer, notifications)
}
