package com.danielsanrocha.scaleye.colormask

import scala.concurrent.ExecutionContext.Implicits.global
import com.danielsanrocha.scaleye.{Color, Image}
import com.danielsanrocha.scaleye.transformers.{ColorConverter, ColorMask}

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {
  val source = "./examples/images/sudoku.jpg"
  val outputFile = "./examples/output/colormask.jpg"

  def main(args: Array[String]): Unit = {

    val img = Image.load(source.toString)

    Await.ready(
      for {
        _ <- ColorConverter(img, ColorConverter.GRAYSCALE)
        _ <- ColorMask(img, Color(0, 0, 0), Color(80, 80, 80))
      } yield {},
      10.seconds
    )

    Image.save(img, outputFile)
  }
}
