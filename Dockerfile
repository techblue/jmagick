FROM dpokidov/imagemagick:7.1.1-10-ubuntu

RUN apt-get update -y
RUN apt-get install -y autoconf automake libtool build-essential pkg-config openjdk-11-jdk

COPY . .

RUN touch NEWS AUTHORS ChangeLog
RUN autoreconf --force --install
RUN automake --add-missing

RUN ./configure --with-java-home=/usr/lib/jvm/java-11-openjdk-amd64
RUN make

# override "dpokidov/imagemagick" default entrypoint
ENTRYPOINT [""]
