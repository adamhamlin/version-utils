# VersionUtils
Easily parse and compare version strings!

```xml
<!-- Available on Maven Central -->
<dependency>
    <groupId>io.github.adamhamlin</groupId>
    <artifactId>version-utils</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

```scala
// Instantiate Version objects from strings
var v = Version("2020.2.8-SNAPSHOT")
v.major // 2020
v.minor // 2
v.patch // 8
v.suffix // "SNAPSHOT"
v.versionStr // "2020.2.8"

v = Version("2.14_test_beta_debug")
v.major // 2
v.minor // 14
v.patch // 0
v.suffix // "test_debug_beta"
v.versionStr // "2.14.0"
```

The `Version` class also implements `Ordered`, so you can compare/sort `Version` objects:

```scala
// Compare
Version("2.3.7") < Version("2.4") // true
Version("1.0") >= Version("0.9.1") // true
Version("6.4") == Version("6.4.0") // true
```
```scala
// Sort
val first = Version("1.0.5")
val second = Version("1.9")
val third = Version("1.12_SNAPSHOT")
Seq(second, third, first).sorted // Seq(first, second, third)
```

You can also pattern match on strings to deconstruct `Version` objects:
```scala
"2020.4_TEST" match {
  case Version(major, minor, patch, suffix) => // 2020, 4, 0, "TEST"
}
```

>You can work with version strings that have 1-3 version parts

>Any missing/non-numeric version parts are defaulted to 0

>The suffix is considered to be everything after the first hyphen or underscore

## Development

### Deploying to Sonatype
In order to publish, you'll need the following:
- A Sonatype account with privileged access for this groupId
- A maven settings file configured with the `ossrh` server and your Sonatype credentials
- A GPG key pair for signing the artifacts

To do a dry-run, execute the following:
```bash
mvn clean install -Prelease --settings /path/to/sonatypeSettings.xml
gpg --verify target/*.pom.asc
# Verify the above command outputted a "Good signature..."
```
Once you're confident, you can actually publish to the nexus using
```bash
mvn clean deploy -Prelease --settings /path/to/sonatypeSettings.xml
```
You can immediately view the artifacts in the [staging repository](https://oss.sonatype.org/#nexus-search;quick~io.github.adamhamlin).

The artifacts should be available on Maven Central within 30 minutes and searchable within 4 hours.

## Future Work
- Option to limit or extend the number of version parts to be used.
- Option to specify a different delimiter for the suffix, or the version parts.
- Add suffix comparison as tiebreaker for Version comparison with same major, minor, patch.