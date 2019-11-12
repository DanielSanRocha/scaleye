package com.danielsanrocha.scaleye.transformers

import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.bytedeco.javacpp.opencv_core

import com.danielsanrocha.scaleye.Color

import com.danielsanrocha.scaleye.linearalgebra.Matrix

class ColorMask(lowerb: Color, upperb: Color) extends Transformer {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def apply(image: Matrix): Future[Unit] = {
    log.debug(
      s"Applying ColorMask with Lower Boundary $lowerb and Upper Boundary $upperb"
    )
    Future {
      opencv_core.inRange(
        image.cvmat,
        new opencv_core.Mat(lowerb.toOopenCV()),
        new opencv_core.Mat(upperb.toOopenCV()),
        image.cvmat
      )
    }
  }
}
