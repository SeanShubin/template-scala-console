package com.seanshubin.template.scala.console.core

import java.time.Duration

class Runner(target: String, emitLine: String => Unit, measureTime: (( => Unit) => Duration), notifications: Notifications) extends Runnable {
  override def run(): Unit = {
    val timeTaken = measureTime {
      emitLine(s"Hello, $target!")
    }
    notifications.timeTaken(timeTaken)
  }
}
