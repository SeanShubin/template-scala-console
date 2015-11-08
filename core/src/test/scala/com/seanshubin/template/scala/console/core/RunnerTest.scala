package com.seanshubin.template.scala.console.core

import java.time.Clock

import com.seanshubin.devon.core.devon.DevonMarshaller
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class RunnerTest extends FunSuite {
  test("application flow") {
    val lines = new ArrayBuffer[String]()
    val emitLine: String => Unit = line => lines.append(line)
    val dummyDevonMarshaller: DevonMarshaller = null
    val clock: Clock = new FakeClock(1000, 11000)
    val timer: Timer = new TimerImpl(clock)
    val notifications: Notifications = new LineEmittingNotifications(dummyDevonMarshaller, emitLine)
    val runner: Runnable = new RunnerImpl("world", emitLine, timer, notifications)
    runner.run()
    assert(lines.size === 2)
    assert(lines(0) === "Hello, world!")
    assert(lines(1) === "10 seconds")
  }
}
