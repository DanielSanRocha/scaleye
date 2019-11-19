package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.Image

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.bytedeco.javacpp.opencv_imgproc

class CannyTransformer(low_threshold: Double, high_threshold: Double) extends Transformer {
  override def apply(image: Image): Future[Unit] = {
    Future {
      opencv_imgproc.Canny(image.cvmat, image.cvmat, low_threshold, high_threshold)
    }
  }
}
