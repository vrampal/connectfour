ConnectFour Demo
===============
This project is a implementation of the [Connect Four game](http://en.wikipedia.org/wiki/Connect_Four) to show some java related technology.

![CodeShip status](https://www.codeship.io/projects/1d88ad50-af84-0131-1f7f-52f111de9aa7/status)

[![Travis Status](https://travis-ci.org/vrampal/connectfour.svg)](https://travis-ci.org/vrampal/connectfour)

[![Coveralls Status](https://coveralls.io/repos/vrampal/connectfour/badge.png)](https://coveralls.io/r/vrampal/connectfour) (only for core module, not yet automated)

Warning: master repository is in [BitBucket](https://bitbucket.org/vrampal/connectfour), not in [GitHub](https://github.com/vrampal/connectfour).

Organization
------------

* connectfour: the root POM, aggregate all the module and provide dependency management
* core: contains all the business layer with tests and serialization example
* cmdline: a very basic command line executable based on core, it also contains basic robots
* webjsp: a very basic web application based on core, it contains JMetter tests (for load) and Selenium test (validation)

Technology used
---------------

Global

* [Git](http://git-scm.com/)
* [Java 7](http://docs.oracle.com/javase/7/docs/) (also check [the specs page](http://docs.oracle.com/javase/specs/))
* [Apache Maven](http://maven.apache.org/pom.html) (also check [this book](http://books.sonatype.com/mvnref-book/reference/))
* [Lombok](http://projectlombok.org/features/)
* [SLF4J](http://www.slf4j.org/manual.html)
* [JUnit 4](https://github.com/junit-team/junit/wiki) with [Harmcrest](https://code.google.com/p/hamcrest/wiki/Tutorial)
* [Mockito](http://docs.mockito.googlecode.com/hg/latest/org/mockito/Mockito.html)

Core

* [Apache Commons](http://commons.apache.org/) (only for Base64), alternative are [Guava](https://code.google.com/p/guava-libraries/) or Java 8

Console

Webjsp

* [Apache Tomcat](http://tomcat.apache.org/)
* Servlet 2.5 / JSP 2.1
* [HTML5](http://www.w3.org/TR/html5/) / [CSS2](http://www.w3.org/TR/CSS2/)

How to build
------------

* mvn install

How to run
----------

Core

* This is a library, it's not runnable.

Console

* TODO

Webjsp

* cd webjsp
* mvn tomcat7:run
* [http://localhost:8080/connect-four-jsp/](http://localhost:8080/connect-four-jsp/)
