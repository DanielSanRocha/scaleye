package com.danielsanrocha.scaleye.linearalgebra

import org.bytedeco.javacpp.opencv_core

class Matrix private[scaleye] (private[scaleye] val cvmat: opencv_core.Mat) {
  override def clone(): Matrix = {
    val mat = new opencv_core.Mat(this.cvmat.size, this.cvmat.`type`)
    this.cvmat.copyTo(mat)

    new Matrix(mat)
  }

  val shape: (Int, Int) = (cvmat.rows(), cvmat.cols())
}

object Matrix {
  def ones(rows: Int, cols: Int): Matrix = {
    new Matrix(opencv_core.Mat.ones(rows, cols, opencv_core.CV_8U).asMat)
  }
}
