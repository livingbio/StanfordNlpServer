FROM dordoka/tomcat


RUN apt-get update && apt-get install make wget  -y

ADD tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml
ADD . /opt/tomcat/webapps/nlp

workdir /opt/tomcat/webapps/nlp
run make build
