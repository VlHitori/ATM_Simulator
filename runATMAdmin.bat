@echo off

for /d %%d in ("C:\Program Files\Java\jdk-*") do (
    set "JAVA_HOME=%%d"
    goto :foundJDK
)

echo JDK not find in C:\Program Files\Java
goto :end

:foundJDK
echo Used jdk with %JAVA_HOME%

set SRC_PATH=src
set BIN_PATH=bin

if not exist "%BIN_PATH%" (
    mkdir %BIN_PATH%
)

"%JAVA_HOME%\bin\javac" -d %BIN_PATH% %SRC_PATH%\*.java

if %errorlevel% neq 0 (
    echo Error
    goto :end
)

"%JAVA_HOME%\bin\java" -cp %BIN_PATH% Main -admin

:end
pause
