#! /usr/bin/bash

# Tested on Fedora 42, Java 21, Python 13
# export JAVA_HOME=/usr/lib/jvm/java
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-11.0.24.0.8-2.fc41.x86_64
# export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export JAVA_HOME=/usr/lib/jvm/java-24-openjdk
export DISABLE_SIKULI_LOG=1
export PATH=$JAVA_HOME/bin:$PATH

# mvn -X -e package
mvn package

if [[ $? -eq 0 ]];
then
    # pip wheel .
    python -m build 
    libdoc --theme DARK target/src/SikuliLibrary docs/SikuliLibrary_dark.html
    libdoc --theme LIGHT target/src/SikuliLibrary docs/SikuliLibrary.html
fi
