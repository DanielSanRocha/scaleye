package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.Color

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.danielsanrocha.scaleye.linearalgebra._
import org.bytedeco.javacpp.opencv_imgproc

object Drawer {
  def apply(img: Matrix, line: Line, color: Color, thickness: Int): Future[Unit] =
    new LineDrawer(line, color, thickness)(img)
  def apply(img: Matrix, point: Point, color: Color, radius: Int): Future[Unit] =
    new PointDrawer(point, color, radius)(img)
}

trait Drawer extends Transformer {}

class LineDrawer(line: Line, color: Color, thickness: Int) extends Drawer {
  override def apply(img: Matrix): Future[Unit] = {
    Future {
      val p1 = line.begin.toOpenCV
      val p2 = line.end.toOpenCV
      opencv_imgproc.line(img.cvmat, p1, p2, color.toOopenCV(), thickness, -1, 0)
    }
  }
}

class PointDrawer(point: Point, color: Color, radius: Int) extends Drawer {
  override def apply(img: Matrix): Future[Unit] = {
    Future {
      val p = point.toOpenCV
      opencv_imgproc.circle(img.cvmat, p, radius, color.toOopenCV(), -1, -1, 0)
    }
  }
}
