package com.danielsanrocha.scaleye.linearalgebra

trait LinearOperations {
  def distance(p0: Point, p1: Point): Double = {
    (p0 - p1).norm()
  }

  def distance(p: Point, l: Line): Double = {
    val v = l.end - l.begin
    val det = l.end.x * l.begin.y - l.end.y * l.begin.x
    val triangle = math.abs(v.y * p.x - v.x * p.y + det)

    triangle / v.norm()
  }

  def distance(l: Line, p: Point): Double = distance(p, l)
}
