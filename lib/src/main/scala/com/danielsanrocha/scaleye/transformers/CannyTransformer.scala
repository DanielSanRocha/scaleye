package com.danielsanrocha.scaleye.transformers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.bytedeco.javacpp.opencv_imgproc

import com.danielsanrocha.scaleye.linearalgebra.Matrix

class CannyTransformer(low_threshold: Double, high_threshold: Double) extends Transformer {
  override def apply(image: Matrix): Future[Unit] = {
    Future {
      opencv_imgproc.Canny(image.cvmat, image.cvmat, low_threshold, high_threshold)
    }
  }
}
