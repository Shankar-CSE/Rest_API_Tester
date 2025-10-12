@echo off
javac -cp "lib\mongodb-driver-sync-4.11.1.jar;lib\mongodb-driver-core-4.11.1.jar;lib\bson-4.11.1.jar" --module-path "C:\javafx\lib" --add-modules javafx.controls -d bin src\App.java src\CRUDfunctionsUI.java src\MongoConnection.java
if %errorlevel% equ 0 (
    java -cp "bin;lib\mongodb-driver-sync-4.11.1.jar;lib\mongodb-driver-core-4.11.1.jar;lib\bson-4.11.1.jar" --module-path "C:\javafx\lib" --add-modules javafx.controls App
)
pause