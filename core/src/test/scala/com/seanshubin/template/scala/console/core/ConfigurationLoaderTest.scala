package com.seanshubin.template.scala.console.core

import java.time.Duration

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class ConfigurationLoaderTest extends FunSuite {
  test("valid configuration") {
    val helper = new Helper(validationResult = Right(Configuration("world")))
    helper.launcher.run()
    assert(helper.sideEffects.size === 2)
    assert(helper.sideEffects(0) ===("notifications.effectiveConfiguration", Configuration("world")))
    assert(helper.sideEffects(1) ===("runner.run", ()))
  }

  test("invalid configuration") {
    val helper = new Helper(validationResult = Left(Seq("error")))
    helper.launcher.run()
    assert(helper.sideEffects.size === 1)
    assert(helper.sideEffects(0) ===("notifications.configurationError", Seq("error")))
  }

  class Helper(validationResult: Either[Seq[String], Configuration]) {
    val sideEffects: ArrayBuffer[(String, Any)] = new ArrayBuffer()
    val configurationFactory = new FakeConfigurationFactory(Seq("foo.txt"), validationResult)
    val runner = new FakeRunner(sideEffects)
    val createRunner: Configuration => Runnable = (theConfiguration) => runner
    val notifications = new FakeNotification(sideEffects)
    val launcher = new ConfigurationLoader(Seq("foo.txt"), configurationFactory, createRunner, notifications)
  }

  class FakeConfigurationFactory(expectedArgs: Seq[String], result: Either[Seq[String], Configuration]) extends ConfigurationFactory {
    override def validate(args: Seq[String]): Either[Seq[String], Configuration] = {
      assert(args === expectedArgs)
      result
    }
  }

  class FakeNotification(sideEffects: ArrayBuffer[(String, Any)]) extends Notifications {
    def append(name: String, value: Any): Unit = {
      sideEffects.append(("notifications." + name, value))
    }

    override def configurationError(lines: Seq[String]): Unit = append("configurationError", lines)

    override def effectiveConfiguration(configuration: Configuration): Unit = append("effectiveConfiguration", configuration)

    override def topLevelException(exception: Throwable): Unit = append("topLevelException", exception)

    override def timeTaken(duration: Duration): Unit = append("timeTaken", duration)
  }

  class FakeRunner(sideEffects: ArrayBuffer[(String, Any)]) extends Runnable {
    override def run(): Unit = sideEffects.append(("runner.run", ()))
  }

}
