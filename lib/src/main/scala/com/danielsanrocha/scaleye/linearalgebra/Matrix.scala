package com.danielsanrocha.scaleye.linearalgebra

trait Matrix[T] {
  def apply(row: Int, column: Int): T

  val shape: (Int, Int)
}
