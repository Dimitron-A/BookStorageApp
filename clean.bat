@echo off
echo Cleaning build files...
del *.class 2>nul
del manifest.txt 2>nul
echo Done!
echo.
echo Note: BookManager.jar and books.dat were not deleted.
pause
