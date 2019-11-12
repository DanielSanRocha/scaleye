package com.danielsanrocha.scaleye

import org.bytedeco.javacpp.{opencv_core => Core}

case class Color(r: Double, g: Double, b: Double, alpha: Double) {
  private[scaleye] def toOopenCV(): Core.Scalar =
    new Core.Scalar(b, g, r, alpha)
}

object Color {
  object RED extends Color(0, 0, 255, 255)
  object GREEN extends Color(0, 255, 0, 255)
  object BLUE extends Color(255, 0, 0, 255)
}
