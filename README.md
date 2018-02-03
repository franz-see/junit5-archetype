[![Build Status](https://travis-ci.org/franz-see/junit5-archetype.svg?branch=master)](https://travis-ci.org/franz-see/junit5-archetype)

# junit5-archetype
maven archetype for creating a project that has junit5 support

## To use this, run the following command:

```
$ mvn archetype:generate \
    -DremoteRepositories=https://bintray.com/franz-see/maven-repo \
    -DarchetypeGroupId=ph.net.see \
    -DarchetypeArtifactId=junit5-archetype \
    -DgroupId=ph.net.see \
    -DartifactId=sonartrade-exercise \
    -Dversion=1.0-SNAPSHOT
```

This will create a maven project from scratch that is configured for JDK8, and JUnit5. In its test directory, it will also contain several feature showcase for JUnit5:

 * Assertions
 * Assumptions
 * `@Disabled`
 * `@DisplayName`
 * Dynamic Tests
 * Parameterized Tests
 * Repeated Tests
 * `@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll`
 * `TestInfo`
 * Tests Tagging
 * Nested tests
 * Test Lifecycle
 * etc

