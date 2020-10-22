package org.adamhamlin.version

import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers._
import org.scalatest.funsuite.AnyFunSuite

class VersionTest extends AnyFunSuite with BeforeAndAfterEach {

  test("Parse version strings") {
    var v = Version("7")
    v.major should be (7)
    v.minor should be (0)
    v.patch should be (0)
    v.suffix should be ("")
    v.versionStr should be ("7.0.0")
    v = Version("2020.3")
    v.major should be (2020)
    v.minor should be (3)
    v.patch should be (0)
    v.suffix should be ("")
    v.versionStr should be ("2020.3.0")
    v = Version("2020.2.8")
    v.major should be (2020)
    v.minor should be (2)
    v.patch should be (8)
    v.suffix should be ("")
    v.versionStr should be ("2020.2.8")
    v = Version("2.14_test_debug_beta")
    v.major should be (2)
    v.minor should be (14)
    v.patch should be (0)
    v.suffix should be ("test_debug_beta")
    v.versionStr should be ("2.14.0")
    v = Version("6.4.x-scala2.11")
    v.major should be (6)
    v.minor should be (4)
    v.patch should be (0)
    v.suffix should be ("scala2.11")
    v.versionStr should be ("6.4.0")
  }

  test("Compare versions") {
    val v1 = Version("2.4.5")
    val v2 = Version("2.4.5")
    val v3 = Version("2.3.7")
    v1 should be (v2)
    v1 should be >= v2
    v1 should be > v3
    v3 should be <= v2
  }

  test("Sort versions") {
    val first = Version("0.4.x")
    val second = Version("0.4.5_DEBUG")
    val third = Version("0.9.0")
    val fourth = Version("0.10.0")
    val fifth = Version("0.44-suffix")
    val sixth = Version("1.1.x")
    val seventh = Version("10.0.x")
    Seq(fifth, second, first, third, sixth, seventh, fourth).sorted should be (
      Seq(first, second, third, fourth, fifth, sixth, seventh)
    )
  }

  test("Pattern match on version string") {
    "2020.4_TEST" match {
      case Version(major, minor, patch, suffix) => {
        major should be (2020)
        minor should be (4)
        patch should be (0)
        suffix should be ("TEST")
      }
    }
  }
}
