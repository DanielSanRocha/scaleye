package com.danielsanrocha.scaleye.houghlines

import scala.concurrent.ExecutionContext.Implicits.global
import com.danielsanrocha.scaleye.{Color, Image}
import com.danielsanrocha.scaleye.extractors.HoughLines
import com.danielsanrocha.scaleye.transformers.{ColorConverter, ColorMask, Drawer}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Main {
  val source = "./examples/images/sudoku.jpg"
  val outputFile = "./examples/output/houghlines.jpg"

  val houghLinesDetector = new HoughLines(1, 3.14 / 180, 200, 160, 5)

  def main(args: Array[String]): Unit = {

    val img = Image.load(source.toString)
    val copy = img.clone()

    Await.ready(
      (for {
        _ <- ColorConverter(copy, ColorConverter.GRAYSCALE)
        _ <- ColorMask(copy, Color(0, 0, 0), Color(80, 80, 80))
        lines <- houghLinesDetector(copy)
      } yield {
        Future.sequence(lines map { line =>
          Drawer(img, line, Color.RED, 2)
        })
      }).flatten,
      10.seconds
    )

    Image.save(img, outputFile)
  }
}
