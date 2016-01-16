package com.seanshubin.template.scala.console.core

import java.time.{Duration, Clock}

import com.seanshubin.devon.core.devon.DevonMarshaller
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class RunnerTest extends FunSuite {
  test("application flow") {
    val lines = new ArrayBuffer[String]()
    val emitLine: String => Unit = line => lines.append(line)
    val dummyDevonMarshaller: DevonMarshaller = null
    val clock: Clock = new ClockStub(1000, 11000)
    val measureTime: (( => Unit) => Duration) = new MeasureTime(clock)
    val notifications: Notifications = new LineEmittingNotifications(dummyDevonMarshaller, emitLine)
    val runner: Runnable = new Runner("world", emitLine, measureTime, notifications)
    runner.run()
    assert(lines.size === 2)
    assert(lines(0) === "Hello, world!")
    assert(lines(1) === "10 seconds")
  }
}
