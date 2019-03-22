### Spring Auto REST Docs JDK 11 bug reproduce case

Setup:
* JDK 11 (openjdk),
* `sourceCompatibility = 11` and
* `capital.scalable:spring-auto-restdocs-json-doclet-jdk9:2.0.4` dependency.

Run `./gradlew build`.

Fails to generate documentation because of missing `jsonDoclet` output.

_but_

Setup:
* JDK 1.8 (Oracle),
* `sourceCompatibility = 1.8` and
* `capital.scalable:spring-auto-restdocs-json-doclet:2.0.4` dependency.

Run `./gradlew build`.

Works as expected.
