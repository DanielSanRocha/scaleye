package com.danielsanrocha.scaleye.processors

import com.danielsanrocha.scaleye.linearalgebra.Matrix

import scala.concurrent.Future

/** A processor is a abstract class/object that takes a image (matrix) as parameter, apply some computation in a different thread and returns a result.
  *
  * A processor can, or not, change the image it is inputted. You should not extend this class directly if you want to write your own computations, see
  * [[extractors.Extractor]] and [[transformers.Transformer]] instead.
  *
  * @tparam T the type of the return after the computation.
  */
trait Processor[T] {
  def apply(img: Matrix): Future[T]
}
