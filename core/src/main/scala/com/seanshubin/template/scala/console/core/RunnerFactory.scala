package com.seanshubin.template.scala.console.core

trait RunnerFactory {
  def createRunner(configuration: Configuration): Runner
}
