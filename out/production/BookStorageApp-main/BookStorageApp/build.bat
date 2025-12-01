@echo off
echo ====================================
echo Building Book Management System
echo ====================================
echo.

echo [1/4] Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)
echo Compilation successful!
echo.

echo [2/4] Creating manifest file...
echo Main-Class: BookManagementGUI > manifest.txt
echo.

echo [3/4] Creating JAR file...
jar cvfm BookManager.jar manifest.txt *.class
if %errorlevel% neq 0 (
    echo ERROR: JAR creation failed!
    pause
    exit /b 1
)
echo.

echo [4/4] Cleaning up...
del manifest.txt
echo.

echo ====================================
echo BUILD SUCCESSFUL!
echo ====================================
echo.
echo JAR file created: BookManager.jar
echo.
echo To run the application:
echo   java -jar BookManager.jar
echo.
pause
