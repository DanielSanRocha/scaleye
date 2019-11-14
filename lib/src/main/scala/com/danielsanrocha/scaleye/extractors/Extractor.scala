package com.danielsanrocha.scaleye.extractors

import com.danielsanrocha.scaleye.processors.Processor

/** A extractor is a abstract class/object that takes a image (matrix) as parameter and apply some computation in a different thread returning some information without altering the original image.
  * @tparam T the type of the return after the computation.
 **/
trait Extractor[T] extends Processor[T] {}
