@echo off
cd src
javac -cp ".;..\lib\App-all.jar" App.java
if %errorlevel% equ 0 (
    java -cp ".;..\lib\App-all.jar" App
)