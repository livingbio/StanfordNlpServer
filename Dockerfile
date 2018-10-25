FROM asia.gcr.io/living-bio/base_images:python3_6_7


ADD tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml
ADD index.html /opt/tomcat/webapps/ROOT/index.html
ADD . /opt/tomcat/webapps/nlp

WORKDIR /opt/tomcat/webapps/nlp
RUN make build
EXPOSE 8080
ADD run.sh /root/run.sh

CMD /root/run.sh
