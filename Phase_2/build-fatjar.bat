@echo off
REM Navigate to the src folder
cd src

REM Compile your Java file with the MongoDB driver jars
javac -cp ".;..\lib\mongodb-driver-sync-4.11.1.jar;..\lib\mongodb-driver-core-4.11.1.jar;..\lib\bson-4.11.1.jar" App.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b
)

REM Create a temporary folder to merge dependencies
mkdir temp
xcopy /s /y ..\lib\*.jar temp >nul

cd temp

REM Extract all jar files into temp
for %%f in (*.jar) do (
    jar xf "%%f"
)
del *.jar

REM Copy the compiled App.class
copy ..\App.class .

REM Create a manifest file
echo Main-Class: App> manifest.txt

REM Build the fat jar
jar cfm ..\App-all.jar manifest.txt *

cd ..
REM Clean up
rd /s /q temp

echo Fat JAR created: App-all.jar
pause
