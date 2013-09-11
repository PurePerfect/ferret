#!/bin/sh

TOMCAT=/home/cfolsom/Development/tools/servers/tomcat/apache-tomcat-7.0.27/webapps
GLASSFISH=/home/cfolsom/Development/tools/servers/glassfish/3.1.2/glassfish/domains/domain1/autodeploy

TODIR=$GLASSFISH

cp -v target/ferret-test.war $TODIR
