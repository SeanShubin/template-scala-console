package com.seanshubin.template.scala.console.domain

trait Timer {
  def trackTime[T](name: String)(f: => T): T
}
