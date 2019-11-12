package com.danielsanrocha.scaleye

import java.nio.file.Paths

import com.danielsanrocha.scaleye.linearalgebra.Matrix
import org.slf4j.LoggerFactory

import org.bytedeco.javacpp.opencv_imgcodecs

object Image {
  class ImageException(msg: String) extends Exception(msg)
  class ImageLoadException(msg: String) extends ImageException(msg)

  private val log = LoggerFactory.getLogger(this.getClass)

  def load(filename: String): Matrix = {
    val path = Paths.get(filename).toString
    log.debug(s"Loading image at $path")
    val mat = opencv_imgcodecs.imread(path)

    if (mat.empty()) {
      throw new ImageLoadException(
        s"Image loaded is empty. Check the provided filename (provided: $filename)"
      )
    }

    new Matrix(mat)
  }

  def save(image: Matrix, filename: String): Unit = {
    val path = Paths.get(filename).toString
    log.debug(s"Saving image at $path")
    opencv_imgcodecs.imwrite(path, image.cvmat)
  }
}
