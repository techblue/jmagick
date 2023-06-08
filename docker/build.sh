#!/bin/bash

autoreconf -vi
./configure --with-java-home=/usr/lib/jvm/java-1.7.0-openjdk-amd64 --prefix=/build
make
make install
