package com.seanshubin.template.scala.console.console

import java.time.{Duration, Clock}

import com.seanshubin.devon.core.devon.{DevonMarshaller, DevonMarshallerWiring}
import com.seanshubin.template.scala.console.core._

trait AfterConfigurationWiring {
  def configuration: Configuration

  lazy val emitLine: String => Unit = println
  lazy val clock: Clock = Clock.systemUTC()
  lazy val devonMarshaller: DevonMarshaller = DevonMarshallerWiring.Default
  lazy val notifications: Notifications = new LineEmittingNotifications(devonMarshaller, emitLine)
  lazy val measureTime: (( => Unit) => Duration) = new MeasureTime(clock)
  lazy val runner: Runnable = new Runner(configuration.greetingTarget, emitLine, measureTime, notifications)
}
