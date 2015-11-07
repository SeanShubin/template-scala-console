package com.seanshubin.template.scala.console.console

import java.nio.charset.{Charset, StandardCharsets}

import com.seanshubin.devon.core.devon.{DevonMarshaller, DevonMarshallerWiring}
import com.seanshubin.template.scala.console.core._
import com.seanshubin.utility.filesystem.{FileSystemIntegration, FileSystemIntegrationImpl}

trait TopLevelWiring {
  def commandLineArguments: Seq[String]

  lazy val emitLine: String => Unit = println
  lazy val fileSystem: FileSystemIntegration = new FileSystemIntegrationImpl
  lazy val devonMarshaller: DevonMarshaller = DevonMarshallerWiring.Default
  lazy val charset: Charset = StandardCharsets.UTF_8
  lazy val notifications: Notifications = new LineEmittingNotifications(devonMarshaller, emitLine)
  lazy val configurationFactory: ConfigurationFactory = new ConfigurationFactoryImpl(
    fileSystem, devonMarshaller, charset)
  lazy val createRunner: Configuration => Runnable = (theConfiguration) => {
    new AfterConfigurationWiring {
      override def configuration: Configuration = theConfiguration
    }.runner
  }
  lazy val runner: Runnable = new ConfigurationLoader(
    commandLineArguments, configurationFactory, createRunner, notifications)
}
