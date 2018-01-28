FROM tomcat:8.5-jre8-slim

MAINTAINER Vincent RAMPAL <vincent.rampal@gmail.com>

ENV CATALINA_BASE /usr/local/connectfour

RUN mkdir -p $CATALINA_BASE;

WORKDIR $CATALINA_BASE

ADD catalina_base ./

RUN adduser -system --no-create-home --group tomcat; \
    chown -R root:tomcat $CATALINA_HOME; \
    chown -R tomcat:tomcat $CATALINA_BASE;

USER tomcat:tomcat
