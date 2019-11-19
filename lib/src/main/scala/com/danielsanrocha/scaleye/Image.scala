package com.danielsanrocha.scaleye

import java.nio.file.Paths

import com.danielsanrocha.scaleye.linearalgebra.Matrix
import org.slf4j.LoggerFactory
import org.bytedeco.javacpp.{opencv_core, opencv_imgcodecs}

class Image private[scaleye] (private[scaleye] val cvmat: opencv_core.Mat) extends Matrix[Color] {
  override def clone(): Image = {
    val mat = new opencv_core.Mat(this.cvmat.size, this.cvmat.`type`)
    this.cvmat.copyTo(mat)

    new Image(mat)
  }

  private val numberOfRows = cvmat.rows()
  private val numberOfColumns = cvmat.cols()
  private val channels = cvmat.channels()

  override def apply(row: Int, column: Int): Color = {
    val blue = cvmat.row(row).data().get(channels * column + 0) & 0xFF
    val green = cvmat.row(row).data().get(channels * column + 1) & 0xFF
    val red = cvmat.row(row).data().get(channels * column + 2) & 0xFF

    Color(red.toInt, green.toInt, blue.toInt)
  }

  val shape: (Int, Int) = (numberOfRows, numberOfColumns)
}

object Image {
  class ImageException(msg: String) extends Exception(msg)
  class ImageLoadException(msg: String) extends ImageException(msg)

  private val log = LoggerFactory.getLogger(this.getClass)

  def load(filename: String): Image = {
    val path = Paths.get(filename).toString
    log.debug(s"Loading image at $path")
    val mat = opencv_imgcodecs.imread(path)

    if (mat.empty()) {
      throw new ImageLoadException(
        s"Image loaded is empty. Check the provided filename (provided: $filename)"
      )
    }

    new Image(mat)
  }

  def save(image: Image, filename: String): Unit = {
    val path = Paths.get(filename).toString
    log.debug(s"Saving image at $path")
    opencv_imgcodecs.imwrite(path, image.cvmat)
  }
}
