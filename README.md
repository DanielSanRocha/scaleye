# Scaleye

A scala wrapper for OpenCV using futures for parallel processing.


## Installation

Scaleye is published at Maven Central. Use the following sbt snippet

```sbt
libraryDependencies ++= Seq(
  "com.danielsanrocha" %% "scaleye" % "[version]"
)
```

Here a list of available version

| Scaleye Version | Scala Version |
| -------------   | ------------- |
|      0.1.0      |     2.13      |

## Sample Usage

The following code snippet show how is easy to execute sequential and parallel image processing tasks using scaleye.
The code load a image located at "./sudoku.jpg" then sequentially 
apply a ColorConverter, a ColorMask transformation and
extracts lines using HoughLines algorithm; after that it draws the extracted lines in the original image (in parallel)
and write the created image at at "./houghlines.jpg'.

```scala
import scala.concurrent.ExecutionContext.Implicits.global
import com.danielsanrocha.scaleye.{Color, Image}
import com.danielsanrocha.scaleye.extractors.HoughLines
import com.danielsanrocha.scaleye.transformers.{
  ColorConverter,
  ColorMask,
  Drawer
}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Main {
  val source = "./sudoku.jpg"
  val outputFile = "./houghlines.jpg"

  val houghLinesDetector = new HoughLines(1, 3.14 / 180, 200, 160, 5)

  def main(args: Array[String]): Unit = {

    val img = Image.load(source.toString)
    val copy = img.clone()

    Await.ready(
      (for {
        _ <- ColorConverter(copy, ColorConverter.GRAYSCALE)
        _ <- ColorMask(copy, Color(0, 0, 0), Color(80, 80, 80))
        lines <- houghLinesDetector(copy)
      } yield {
        Future.sequence(lines map { line =>
          Drawer(img, line, Color.RED, 2)
        })
      }).flatten,
      10.seconds
    )

    Image.save(img, outputFile)
  }
}
```  

Applying the above transformation for the image in "examples/images/sudoku.jpg" yields the following result:

<div align="center"> 
  <img src="https://raw.githubusercontent.com/danielsanrocha/scaleye/master/examples/images/sudoku_houghlines.png" width="500px" />
</div>

Other examples can be found in the "examples/src/main/scala" folder in this repo.

## Contributing

Any contribution is welcome =)

## License

This project is licensed under the Apache 2.0 license - see the [LICENSE.md](LICENSE.md) file for details.

## Contributors

[Daniel Santana Rocha](https://github.com/danielsanrocha/)
