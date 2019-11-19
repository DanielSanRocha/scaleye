package com.danielsanrocha.scaleye

import org.scalatest.{FunSpec, Matchers}

class ImageSpec extends FunSpec with Matchers {
  private val redImagePath = this.getClass.getResource("/fixtures/images/red.png").getPath
  private val greenImagePath = this.getClass.getResource("/fixtures/images/green.png").getPath
  private val blueImagePath = this.getClass.getResource("/fixtures/images/blue.png").getPath

  private val purpleImagePath = this.getClass.getResource("/fixtures/images/purple.png").getPath

  private val flowerImagePath = this.getClass.getResource("/fixtures/images/flower.jpg").getPath

  describe("Test Load method") {
    it("should successfully load a valid jpg image") {
      val image = Image.load(flowerImagePath)
      assert(image != null)
    }

    it("should throw a ImageLoadException if path is non existent") {
      an[Image.ImageLoadException] should be thrownBy Image.load("/invalid/path")
    }
  }

  describe("Test shape") {
    it("should load a jpg image with the correct shape") {
      val image = Image.load(flowerImagePath)

      assert(image.shape == (1500, 2265))
    }

    it("should load a png image with the correct shape") {
      val image = Image.load(greenImagePath)

      assert(image.shape == (50, 50))
    }
  }

  describe("Test clone method") {
    it("should successfully clone a valid image") {
      val image = Image.load(flowerImagePath)
      val copy = image.clone()

      assert(image != copy)
    }

    it("the clone image should have the same pixels values") {
      val image = Image.load(flowerImagePath)
      val copy = image.clone()

      assert(image(10, 12) == copy(10, 12))
      assert(image(22, 3) == copy(22, 3))
    }
  }

  describe("Get Pixel (apply method)") {
    it("should correctly return a pixel from a completely green image") {
      val greenImage = Image.load(greenImagePath)

      val color = greenImage(7, 20)

      assert(color == Color.GREEN)
    }

    it("should correctly return a pixel from a completely red image") {
      val redImage = Image.load(redImagePath)

      val color = redImage(19, 20)

      assert(color == Color.RED)
    }
    it("should correctly return a pixel from a completely blue image") {
      val blueImage = Image.load(blueImagePath)

      val color = blueImage(10, 20)

      assert(color == Color.BLUE)
    }

    it("should correctly return a pixel from a completely purple image") {
      val purpleImage = Image.load(purpleImagePath)

      val color = purpleImage(13, 12)

      assert(color == Color(140, 66, 168))
    }
  }
}
