# Triennium

Triennium is a small library that strives to provide [efficient
topic routing]() implementations for RabbitMQ and MQTT topic schemes.


## Project Goals

 * Be correct
 * Be efficient
 * Have solid test coverage


## Project Maturity

Triennium is *very* young.


## Artifacts

Triennium artifacts are [released to
Clojars](https://clojars.org/clojurewerkz/triennium). If you are using
Maven, add the following repository definition to your `pom.xml`:

``` xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
```

### The Most Recent Release

With Leiningen:

    [clojurewerkz/triennium "1.0.0-alpha1"]


With Maven:

    <dependency>
      <groupId>clojurewerkz</groupId>
      <artifactId>triennium</artifactId>
      <version>1.0.0-alpha1</version>
    </dependency>


## Documentation & Examples

This section will be updated as project API matures.


## Community & Support

Feel free to use GitHub issues to ask any questions you may have.

To subscribe for announcements of releases, important changes and so
on, please follow [@ClojureWerkz](https://twitter.com/clojurewerkz) on
Twitter.



## Supported Clojure versions

Triennium is built from the ground up for Clojure 1.5.1 and up.


## Continuous Integration Status

[![Continuous Integration status](https://secure.travis-ci.org/clojurewerkz/triennium.png)](http://travis-ci.org/clojurewerkz/triennium)


## Triennium Is a ClojureWerkz Project

Triennium is part of the [group of Clojure libraries known as ClojureWerkz](http://clojurewerkz.org), together with

 * [Monger](http://clojuremongodb.info)
 * [Langohr](http://clojurerabbitmq.info)
 * [Elastisch](http://clojureelasticsearch.info)
 * [Cassaforte](http://clojurecassandra.info)
 * [Meltdown](https://github.com/clojurewerkz/meltdown)
 * [Neocons](http://clojureneo4j.info)
 * [EEP](https://github.com/clojurewerkz/eep)

and several others.


## Development

Triennium uses [Leiningen
2](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md). Make
sure you have it installed and then run tests against supported
Clojure versions using

    lein all test

Then create a branch and make your changes on it. Once you are done
with your changes and all tests pass, submit a pull request on GitHub.



## License

Copyright (C) 2014 Michael S. Klishin, Alex Petrov, and The ClojureWerkz Team.

Double licensed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html) (the same as Clojure) or
the [Apache Public License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
