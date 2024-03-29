ConnectFour Demo
===============
This project is a implementation of the [Connect Four game](http://en.wikipedia.org/wiki/Connect_Four) to show some java related technology.

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
* [Java 8](https://docs.oracle.com/javase/8/docs/) (also check [the specs page](http://docs.oracle.com/javase/specs/))
* [Apache Maven](http://maven.apache.org/pom.html)
* [Lombok](http://projectlombok.org/features/) (requires to be installed in your IDE)
* [SLF4J](http://www.slf4j.org/manual.html)
* [JUnit 4](https://github.com/junit-team/junit/wiki) with [Harmcrest](https://code.google.com/p/hamcrest/wiki/Tutorial)
* [Mockito](http://site.mockito.org/)

Core

* [Google GSON](https://github.com/google/gson/blob/master/UserGuide.md)
* [Fastxml Jackson](https://github.com/FasterXML/jackson)
* [Xstream](http://x-stream.github.io/)
* [JBehave](http://jbehave.org/reference/stable/getting-started.html) (in src/test/jbehave, not run by maven)

Console

* [Proguard](http://proguard.sourceforge.net/) (not yet integrated)

Webjsp

* [Apache Tomcat 7+](http://tomcat.apache.org/)
* [Jetty](http://www.eclipse.org/jetty/)
* Servlet 3.0 / JSP 2.2 (check JavaEE [API](http://docs.oracle.com/javaee/6/api/) and [tutorial](http://docs.oracle.com/javaee/6/tutorial/doc/))
* [HTML5](http://www.w3.org/TR/html5/) / [CSS2](http://www.w3.org/TR/CSS2/)
* [Apache JMeter](https://jmeter.apache.org/) (in src/test/jmeter, not run by maven)
* [Selenium](http://docs.seleniumhq.org/) (in src/test/selenium, not run by maven)

How to build
------------

* mvn install

How to run
----------

Core

* This is a library, it's not runnable.

Console

* TODO

Webjsp (with Tomcat)

* cd webjsp
* mvn tomcat7:run
* [http://localhost:8080/connectfour/](http://localhost:8080/connectfour/)

Webjsp (with Jetty)

* cd webjsp
* mvn jetty:run
* [http://localhost:8080/connectfour/index.html](http://localhost:8080/connectfour/index.html)

License
-------

[MIT license](http://opensource.org/licenses/MIT)
Copyright (c) 2014-2021 Vincent RAMPAL


