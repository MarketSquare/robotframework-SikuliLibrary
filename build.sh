#! /usr/bin/bash

# Tested on Fedora 42, Java 21, Python 13
export JAVA_HOME=/usr/lib/jvm/java
export DISABLE_SIKULI_LOG=1

mvn package

if [[ $? -eq 0 ]];
then
    pip wheel .
fi
