package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.linearalgebra.Matrix
import org.bytedeco.javacpp.opencv_imgproc
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MorphologicalTransformer(operation: MorphologicalTransformer.Operation, kernel: Matrix) extends Transformer {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def apply(img: Matrix): Future[Unit] = {
    log.info("Applying MorphologicalTransfomer on a matrix")
    Future {
      opencv_imgproc.morphologyEx(img.cvmat, img.cvmat, operation.cvValue, kernel.cvmat)
    }
  }
}

object MorphologicalTransformer {
  sealed abstract class Operation(private[MorphologicalTransformer] val cvValue: Int)

  object ERODE extends Operation(opencv_imgproc.MORPH_ERODE)
  object DILATE extends Operation(opencv_imgproc.MORPH_DILATE)
  object OPEN extends Operation(opencv_imgproc.MORPH_OPEN)
  object CLOSE extends Operation(opencv_imgproc.MORPH_CLOSE)
  object GRADIENT extends Operation(opencv_imgproc.MORPH_GRADIENT)

  def apply(img: Matrix, operation: MorphologicalTransformer.Operation, kernel: Matrix): Future[Unit] =
    new MorphologicalTransformer(operation, kernel)(img)

  def apply(img: Matrix, operation: MorphologicalTransformer.Operation, kernelSize: Int): Future[Unit] =
    new MorphologicalTransformer(operation, Matrix.ones(kernelSize, kernelSize))(img)
}
