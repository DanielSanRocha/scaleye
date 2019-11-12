package com.danielsanrocha.scaleye.processors

import com.danielsanrocha.scaleye.linearalgebra.Matrix

import scala.concurrent.Future

trait Processor[T] {
  def apply(img: Matrix): Future[T]
}
