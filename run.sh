#!/bin/bash
set -m  
/opt/tomcat/bin/catalina.sh start
tail -f /opt/tomcat/logs/catalina.out

