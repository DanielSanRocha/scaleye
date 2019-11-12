package com.danielsanrocha.scaleye.grayscale

import com.danielsanrocha.scaleye.Image
import com.danielsanrocha.scaleye.transformers.ColorConverter

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {
  val source = "./examples/images/flower.jpg"
  val outputFile = "./examples/output/grayscale.jpg"

  def main(args: Array[String]): Unit = {

    val img = Image.load(source.toString)

    Await.ready(ColorConverter(img, ColorConverter.GRAYSCALE), 10.seconds)

    Image.save(img, outputFile)
  }
}
