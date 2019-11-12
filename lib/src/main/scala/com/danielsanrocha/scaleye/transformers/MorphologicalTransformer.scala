package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.linearalgebra.Matrix
import org.bytedeco.javacpp.opencv_imgproc
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MorphologicalTransformer(operation: Int, kernel: Matrix) extends Transformer {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def apply(img: Matrix): Future[Unit] = {
    log.info("Applying MorphologicalTransfomer on a matrix")
    Future {
      opencv_imgproc.morphologyEx(
        img.cvmat,
        img.cvmat,
        operation,
        kernel.cvmat
      )
    }
  }
}
