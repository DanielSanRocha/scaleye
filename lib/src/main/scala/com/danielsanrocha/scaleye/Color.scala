package com.danielsanrocha.scaleye

import org.bytedeco.javacpp.{opencv_core => Core}

case class Color(r: Double, g: Double, b: Double) {
  private[scaleye] def toOopenCV(): Core.Scalar =
    new Core.Scalar(b, g, r, 255)
}

object Color {
  object RED extends Color(255, 0, 0)
  object GREEN extends Color(0, 255, 0)
  object BLUE extends Color(0, 0, 255)

  def apply(r: Double, g: Double, b: Double): Color = new Color(r, g, b)
}
