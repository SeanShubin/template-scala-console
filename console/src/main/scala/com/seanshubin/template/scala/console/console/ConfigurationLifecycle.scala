package com.seanshubin.template.scala.console.console

import java.time.{Clock, Duration}

import com.seanshubin.devon.domain.DevonMarshallerWiring
import com.seanshubin.devon.parserules.DevonMarshaller
import com.seanshubin.template.scala.console.core._

trait ConfigurationLifecycle {
  def configuration: Configuration

  lazy val emitLine: String => Unit = println
  lazy val clock: Clock = Clock.systemUTC()
  lazy val devonMarshaller: DevonMarshaller = DevonMarshallerWiring.Default
  lazy val notifications: Notifications = new LineEmittingNotifications(devonMarshaller, emitLine)
  lazy val measureTime: ((=> Unit) => Duration) = new MeasureTime(clock)
  lazy val timer: Timer = new TimerImpl(clock, notifications.startTiming, notifications.endTiming)
  lazy val runner: Runnable = new Runner(configuration.greetingTarget, emitLine, timer.trackTime)
}
