package com.seanshubin.template.scala.console.console

import com.seanshubin.template.scala.console.core.{Configuration, Runner, RunnerFactory}

class RunnerFactoryImpl extends RunnerFactory {
  override def createRunner(theConfiguration: Configuration): Runner = {
    new RunnerWiring {
      override def configuration: Configuration = theConfiguration
    }.runner
  }
}
