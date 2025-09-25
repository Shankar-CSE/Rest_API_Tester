@echo off
cd src
javac -cp ".;..\lib\App-all.jar" -d ../bin App.java
if %errorlevel% equ 0 (
    java -cp ".;..\lib\App-all.jar"  App
)