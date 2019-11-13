package com.danielsanrocha.scaleye.extractors

import com.danielsanrocha.scaleye.linearalgebra.{Line, Matrix, Point}
import org.bytedeco.javacpp.indexer.IntRawIndexer
import org.bytedeco.javacpp.{opencv_core, opencv_imgproc}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class HoughLines(rho: Double, theta: Double, threshold: Int, minLineLength: Double, maxLineGap: Double) extends Extractor[Seq[Line]] {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def apply(image: Matrix): Future[Seq[Line]] = {
    log.debug(s"Applying HoughLinesP to a image. Parameters: (rho -> ${rho}, theta -> ${theta}, threshold -> ${threshold})")

    Future {
      val linesMatrix = new opencv_core.Mat()

      opencv_imgproc.HoughLinesP(image.cvmat, linesMatrix, rho, theta, threshold, minLineLength, maxLineGap)

      if (linesMatrix.empty()) {
        Seq()
      } else {
        val lineIndexer: IntRawIndexer = linesMatrix.createIndexer()
        0 until linesMatrix.rows() map { i =>
          val x1 = lineIndexer.get(i, 0)
          val y1 = lineIndexer.get(i, 1)
          val x2 = lineIndexer.get(i, 2)
          val y2 = lineIndexer.get(i, 3)
          Line(Point(x1, y1), Point(x2, y2))
        }
      }
    }
  }
}
