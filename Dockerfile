FROM java:8

ADD . /work
WORKDIR /work

RUN ./install.sh

EXPOSE 9998
CMD ./run.sh
