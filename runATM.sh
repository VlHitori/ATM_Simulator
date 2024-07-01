#!/bin/bash
# Этот скрипт компилирует и запускает Java проект
# Проект - симулятор банкомата

SRC_PATH=src
BIN_PATH=bin

if [ ! -d "$BIN_PATH" ]; then
    mkdir $BIN_PATH
fi

javac -d $BIN_PATH $SRC_PATH/*.java

java -cp $BIN_PATH Main "$@"
