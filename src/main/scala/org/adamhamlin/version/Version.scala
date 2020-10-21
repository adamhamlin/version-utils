package org.adamhamlin.version

import scala.util.control.Exception.allCatch

/**
 * Utility class to parse and compare version strings.
 * NOTE: Can use a maximum of three version parts, i.e., X.X.X
 */
case class Version(major: Int, minor: Int = 0, patch: Int = 0, suffix: String = "") extends Ordered[Version] {
  val versionStr: String = s"$major.$minor.$patch"

  private val comparisonTuple = (major, minor, patch)

  override def compare(other: Version): Int = {
    Ordering[(Int, Int, Int)].compare(comparisonTuple, other.comparisonTuple)
  }

  override def toString: String = {
    s"Version($major, $minor, $patch${if (suffix.isEmpty) "" else s", suffix = '$suffix'"})"
  }
}

object Version {
  private val VersionParts: Int = 3
  private val VersionPartsDelimiterRegex: String = "\\."
  private val SuffixDelimiterRegex: String = "-|_"

  /**
   * Create a new Version instance by parsing the provided version string.
   */
  def apply(versionStr: String): Version = {
    def splitVersion(str: String): Seq[String] = str.split(VersionPartsDelimiterRegex, VersionParts).toSeq

    // First, split off an optional suffix at the first suffix delimiter
    val (versions, suffix) = versionStr.split(SuffixDelimiterRegex, 2).toSeq match {
      case version +: Nil => (splitVersion(version), "")
      case version +: suffix +: Nil => (splitVersion(version), suffix)
    }
    // Attempt to coerce each version element to Int. Default to 0 if missing/non-numeric/etc.
    val versionList: Seq[Int] = versions.flatMap(v => allCatch.opt(v.toInt)).padTo(VersionParts, 0)

    Version(versionList(0), versionList(1), versionList(2), suffix)
  }

  /**
   * Match on String and extract Version parameters.
   */
  def unapply(arg: String): Option[(Int, Int, Int, String)] = Option(arg).map(_ => {
    val v = Version(arg)
    (v.major, v.minor, v.patch, v.suffix)
  })
}

