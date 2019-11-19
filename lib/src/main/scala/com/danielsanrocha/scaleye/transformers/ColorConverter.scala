package com.danielsanrocha.scaleye.transformers

import com.danielsanrocha.scaleye.Image
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.bytedeco.javacpp.opencv_imgproc

class ColorConverter(mask: ColorConverter.Mask) extends Transformer {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def apply(image: Image): Future[Unit] = {
    log.debug(s"Applying ColorConverter to a image. Mask: $mask")

    Future {
      opencv_imgproc.cvtColor(image.cvmat, image.cvmat, mask.cvValue)
    }
  }
}

object ColorConverter {
  sealed abstract class Mask(private[ColorConverter] val cvValue: Int)
  case object GRAYSCALE extends Mask(opencv_imgproc.COLOR_BGR2GRAY)

  def apply(img: Image, mask: ColorConverter.Mask): Future[Unit] = {
    new ColorConverter(mask)(img)
  }
}
