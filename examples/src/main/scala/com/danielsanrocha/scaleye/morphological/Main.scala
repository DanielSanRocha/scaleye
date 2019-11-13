package com.danielsanrocha.scaleye.morphological

import com.danielsanrocha.scaleye.Image
import com.danielsanrocha.scaleye.transformers.MorphologicalTransformer

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {
  val source = "./examples/images/j_letter.png"
  val outputFile = "./examples/output/morphological.png"

  def main(args: Array[String]): Unit = {

    val img = Image.load(source.toString)

    Await.ready(MorphologicalTransformer(img, MorphologicalTransformer.GRADIENT, 4), 10.seconds)

    Image.save(img, outputFile)
  }
}
