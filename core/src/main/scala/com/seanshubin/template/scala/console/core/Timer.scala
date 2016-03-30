package com.seanshubin.template.scala.console.core

trait Timer {
  def trackTime[T](name: String)(f: => T): T
}
