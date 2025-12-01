@echo off
echo Building Book Management System JAR...

REM Compile all Java files
javac -d bin *.java

REM Create manifest file
echo Main-Class: BookManagementGUI > manifest.txt

REM Create JAR file
jar cfm BookManagementSystem.jar manifest.txt -C bin .

REM Clean up
del manifest.txt

echo.
echo Build complete! BookManagementSystem.jar created.
echo Double-click the JAR file to run the application.
pause
