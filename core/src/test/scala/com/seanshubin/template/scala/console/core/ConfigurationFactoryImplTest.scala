package com.seanshubin.template.scala.console.core

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Path, Paths}

import com.seanshubin.devon.core.devon.DevonMarshallerWiring
import com.seanshubin.utility.filesystem.FileSystemIntegrationNotImplemented
import org.scalatest.FunSuite

class ConfigurationFactoryImplTest extends FunSuite {
  test("complete configuration") {
    val content =
      """{
        |  greetingTarget world
        |}
        | """.stripMargin

    val expected = Right(Configuration("world"))

    val configurationFactory = createConfigurationFactory(configFileName = "environment.txt", content = content, exists = true)
    val actual = configurationFactory.validate(Seq("environment.txt"))
    assert(actual === expected)
  }

  test("missing configuration file") {
    val content =
      """{
        |  servePathOverride gui/src/main/resources/
        |  optionalPathPrefix /template
        |}
        | """.stripMargin

    val expected = Left(Seq("Configuration file named 'environment.txt' not found"))

    val configurationFactory = createConfigurationFactory(configFileName = "environment.txt", content = content, exists = false)
    val actual = configurationFactory.validate(Seq("environment.txt"))
    assert(actual === expected)
  }

  test("malformed configuration") {
    val content = "{"

    val expected = Left(Seq("There was a problem reading the configuration file 'environment.txt': Could not match 'element', expected one of: map, array, string, null"))

    val configurationFactory = createConfigurationFactory(configFileName = "environment.txt", content = content, exists = true)
    val actual = configurationFactory.validate(Seq("environment.txt"))
    assert(actual === expected)
  }

  def createConfigurationFactory(configFileName: String, content: String, exists: Boolean): ConfigurationFactory = {
    val configFilePath = Paths.get(configFileName)
    val devonMarshaller = DevonMarshallerWiring.Default
    val fileSystem = new FakeFileSystem(configFilePath = configFilePath, content = content, exists = exists)
    val configurationFactory = new ConfigurationFactoryImpl(fileSystem, devonMarshaller, charset)
    configurationFactory
  }

  val charset: Charset = StandardCharsets.UTF_8

  class FakeFileSystem(configFilePath: Path, content: String, exists: Boolean) extends FileSystemIntegrationNotImplemented {
    override def exists(path: Path): Boolean = {
      assert(path === configFilePath)
      exists
    }

    override def readAllBytes(path: Path): Seq[Byte] = {
      assert(path === configFilePath)
      content.getBytes(charset)
    }
  }

}
