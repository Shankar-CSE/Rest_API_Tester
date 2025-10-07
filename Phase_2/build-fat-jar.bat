@echo off
echo Building Fat JAR for REST API Tester...

REM Clean previous builds
if exist "dist" rmdir /s /q dist
mkdir dist
mkdir dist\temp

REM Compile Java sources
echo Compiling Java sources...
cd src
javac -cp ".;..\lib\mongodb-driver-sync-4.11.1.jar;..\lib\mongodb-driver-core-4.11.1.jar;..\lib\bson-4.11.1.jar" -d ..\dist\temp *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    cd ..
    pause
    exit /b 1
)
cd ..

REM Extract dependencies
echo Extracting dependencies...
cd dist\temp

REM Extract MongoDB driver JARs
jar -xf ..\..\lib\mongodb-driver-sync-4.11.1.jar
jar -xf ..\..\lib\mongodb-driver-core-4.11.1.jar
jar -xf ..\..\lib\bson-4.11.1.jar

REM Create manifest file
echo Main-Class: App > manifest.txt
echo. >> manifest.txt

REM Create the fat JAR
echo Creating fat JAR...
jar -cfm ..\RestAPITester.jar manifest.txt *

cd ..\..

REM Clean up temporary files
rmdir /s /q dist\temp

echo Fat JAR created successfully: dist\RestAPITester.jar
echo You can run it with: java -jar dist\RestAPITester.jar
pause