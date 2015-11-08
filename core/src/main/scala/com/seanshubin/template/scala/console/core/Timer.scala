package com.seanshubin.template.scala.console.core

import java.time.Duration

trait Timer {
  def measureTime(block: => Unit): Duration
}
