@echo off
echo Starting Book Management System...
echo.
java -jar BookManager.jar
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Failed to run the application!
    echo Make sure BookManager.jar exists.
    echo Run build.bat first to create it.
    pause
)
