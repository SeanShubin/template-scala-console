package com.seanshubin.template.scala.console.core

class Runner(target: String, emitLine: String => Unit, trackTime: String => (=> Unit) => Unit) extends Runnable {
  override def run(): Unit = {
    trackTime("total") {
      emitLine(s"Hello, $target!")
    }
  }
}
