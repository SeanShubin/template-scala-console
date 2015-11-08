package com.seanshubin.template.scala.console.core

class RunnerImpl(target: String, emitLine: String => Unit, timer: Timer, notifications: Notifications) extends Runnable {
  override def run(): Unit = {
    val timeTaken = timer.measureTime {
      emitLine(s"Hello, $target!")
    }
    notifications.timeTaken(timeTaken)
  }
}
