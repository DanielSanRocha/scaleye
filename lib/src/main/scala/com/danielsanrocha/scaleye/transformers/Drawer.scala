package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.Color

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.danielsanrocha.scaleye.linearalgebra.{Matrix, Point, Line}
import org.bytedeco.javacpp.opencv_imgproc

/** Draw simple geometric forms in a image. **/
object Drawer {

  /** Draw a line in a image.
    * @param img - Image to draw; this method will alter the original image.
    * @param line - Object that holds the position of the line that will be draw, for more information see [[linearalgebra.Line]].
    * @param color - The color of the line, for more information see [[Color]].
    * @param thickness - The thickness of the line as a integer.
    * **/
  def apply(img: Matrix, line: Line, color: Color, thickness: Int): Future[Unit] =
    new LineDrawer(line, color, thickness)(img)

  /** Draw a point (filled circle) in a image.
    * @param img - Image to draw; this method will alter the original image.
    * @param point - Object that holds the position of the point that will be draw, for more information see [[linearalgebra.Point]].
    * @param color - The color of the point, for more information see [[Color]].
    * @param radius - The radius of the point as a integer.
    * **/
  def apply(img: Matrix, point: Point, color: Color, radius: Int): Future[Unit] =
    new PointDrawer(point, color, radius)(img)
}

private[transformers] trait Drawer extends Transformer {}

/** Create a transformer that draw a line into images.
  * @param line - Object that holds the position of the line that will be draw, for more information see [[linearalgebra.Line]].
  * @param color - The color of the line, for more information see [[Color]].
  * @param thickness - The thickness of the line as a integer.
  * **/
class LineDrawer(line: Line, color: Color, thickness: Int) extends Drawer {
  override def apply(img: Matrix): Future[Unit] = {
    Future {
      val p1 = line.begin.toOpenCV
      val p2 = line.end.toOpenCV
      opencv_imgproc.line(img.cvmat, p1, p2, color.toOopenCV(), thickness, -1, 0)
    }
  }
}

/** Draw a transformer that draw a point into images.
  * @param point - Object that holds the position of the point that will be draw, for more information see [[linearalgebra.Point]].
  * @param color - The color of the point, for more information see [[Color]].
  * @param radius - The radius of the point as a integer.
  * **/
class PointDrawer(point: Point, color: Color, radius: Int) extends Drawer {
  override def apply(img: Matrix): Future[Unit] = {
    Future {
      val p = point.toOpenCV
      opencv_imgproc.circle(img.cvmat, p, radius, color.toOopenCV(), -1, -1, 0)
    }
  }
}
