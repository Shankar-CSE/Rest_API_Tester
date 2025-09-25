@echo off
cd src
javac -cp ".;..\lib\mongodb-driver-sync-4.11.1.jar;..\lib\mongodb-driver-core-4.11.1.jar;..\lib\bson-4.11.1.jar" -d ../bin App.java
if %errorlevel% equ 0 (
    cd ../bin
    java -cp ".;..\lib\mongodb-driver-sync-4.11.1.jar;..\lib\mongodb-driver-core-4.11.1.jar;..\lib\bson-4.11.1.jar" App
)