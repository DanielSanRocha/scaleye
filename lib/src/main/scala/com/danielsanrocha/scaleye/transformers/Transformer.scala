package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.processors.Processor

/** A transformer is a abstract class/object that takes a image (matrix) as parameter and apply some computation in a different thread that change the original image.*/
trait Transformer extends Processor[Unit] {}
