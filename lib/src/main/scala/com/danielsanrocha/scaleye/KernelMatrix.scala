package com.danielsanrocha.scaleye

import com.danielsanrocha.scaleye.linearalgebra.Matrix
import org.bytedeco.javacpp.opencv_core

class KernelMatrix private[scaleye] (private[scaleye] val cvmat: opencv_core.Mat) extends Matrix[Double] {
  override def clone(): KernelMatrix = {
    val mat = new opencv_core.Mat(this.cvmat.size, this.cvmat.`type`)
    this.cvmat.copyTo(mat)

    new KernelMatrix(mat)
  }

  def apply(x: Int, y: Int): Double = {
    2d
  }

  val shape: (Int, Int) = (cvmat.rows(), cvmat.cols())
}

object KernelMatrix {
  def ones(rows: Int, cols: Int): KernelMatrix = {
    new KernelMatrix(opencv_core.Mat.ones(rows, cols, opencv_core.CV_8U).asMat)
  }
}
