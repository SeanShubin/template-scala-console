package com.seanshubin.template.scala.console.core

import java.nio.charset.Charset
import java.nio.file.Paths

import com.seanshubin.devon.core.devon.DevonMarshaller

class ValidateConfiguration(files: FilesContract,
                            devonMarshaller: DevonMarshaller,
                            charset: Charset) extends (Seq[String] => Either[Seq[String], Configuration]) {
  private val sampleConfiguration: Configuration = Configuration(
    greetingTarget = "world"
  )

  override def apply(args: Seq[String]): Either[Seq[String], Configuration] = {
    if (args.length == 1) {
      val configFilePath = Paths.get(args(0))
      try {
        if (files.exists(configFilePath)) {
          val bytes = files.readAllBytes(configFilePath)
          val text = new String(bytes.toArray, charset)
          val devon = devonMarshaller.fromString(text)
          val config = devonMarshaller.toValue(devon, classOf[Configuration])
          Right(config)
        } else {
          Left(Seq(s"Configuration file named '$configFilePath' not found"))
        }
      } catch {
        case ex: Throwable =>
          Left(Seq(s"There was a problem reading the configuration file '$configFilePath': ${ex.getMessage}"))
      }
    } else {
      val sampleConfigDevon = devonMarshaller.fromValue(sampleConfiguration)
      val prettySampleLines = devonMarshaller.toPretty(sampleConfigDevon)
      Left(Seq(
        "Expected exactly one argument, the name of the configuration file",
        "A typical configuration file might look something like this",
        "") ++ prettySampleLines)
    }
  }
}
