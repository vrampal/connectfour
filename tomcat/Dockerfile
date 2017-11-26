FROM tomcat:8.5-jre8

MAINTAINER Vincent RAMPAL <vincent.rampal@gmail.com>

ENV CATALINA_BASE /usr/local/connectfour
RUN mkdir -p "$CATALINA_BASE"
WORKDIR $CATALINA_BASE
ADD catalina_base ./ 
