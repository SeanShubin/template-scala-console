package com.seanshubin.template.scala.console.core

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class RunnerTest extends FunSuite {
  test("application flow") {
    val lines = new ArrayBuffer[String]()
    val emitLine: String => Unit = line => lines.append(line)
    val runner: Runner = new RunnerImpl("world", emitLine)
    runner.run()
    assert(lines.size === 1)
    assert(lines(0) === "Hello, world!")
  }
}
