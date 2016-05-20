FROM ubuntu:14.04

RUN apt-get update
RUN apt-get install -y openjdk-7-jdk libmagickcore-dev libmagickwand-dev make

ADD docker/build.sh /
RUN chmod +x /build.sh
WORKDIR /src
VOLUME /src
VOLUME /build
CMD /build.sh



