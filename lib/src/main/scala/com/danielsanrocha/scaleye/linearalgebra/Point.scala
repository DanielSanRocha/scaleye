package com.danielsanrocha.scaleye.linearalgebra

import org.bytedeco.javacpp.opencv_core

case class Point(x: Double, y: Double) {
  def norm(): Double = {
    math.sqrt(x * x + y * y)
  }

  def normalized(): Point = this / this.norm()

  def +(that: Point): Point = Point(this.x + that.x, this.y + that.y)
  def -(that: Point): Point = Point(this.x - that.x, this.y - that.y)

  def /(c: Double): Point = Point(x / c, y / c)

  def unary_-(): Point = Point(-x, -y)

  private[scaleye] def toOpenCV: opencv_core.Point = {
    new opencv_core.Point(math.round(x).toInt, math.round(y).toInt)
  }
}
