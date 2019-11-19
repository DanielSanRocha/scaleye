package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.{Image, KernelMatrix}
import com.danielsanrocha.scaleye.linearalgebra.Matrix
import org.bytedeco.javacpp.opencv_imgproc
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MorphologicalTransformer(operation: MorphologicalTransformer.Operation, kernel: KernelMatrix) extends Transformer {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def apply(img: Image): Future[Unit] = {
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

  def apply(img: Image, operation: MorphologicalTransformer.Operation, kernel: KernelMatrix): Future[Unit] =
    new MorphologicalTransformer(operation, kernel)(img)

  def apply(img: Image, operation: MorphologicalTransformer.Operation, kernelSize: Int): Future[Unit] =
    new MorphologicalTransformer(operation, KernelMatrix.ones(kernelSize, kernelSize))(img)
}
