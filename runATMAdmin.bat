@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-22

set SRC_PATH=src

set BIN_PATH=bin

if not exist "%BIN_PATH%" (
    mkdir %BIN_PATH%
)

"%JAVA_HOME%\bin\javac" -d %BIN_PATH% %SRC_PATH%\*.java

"%JAVA_HOME%\bin\java" -cp %BIN_PATH% Main -admin
