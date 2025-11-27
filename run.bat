@echo off
REM Quick run script for Windows

echo ========================================
echo  Student Management System
echo ========================================
echo.

REM Check if bin directory exists
if not exist "bin" (
    echo Error: bin directory not found!
    echo Please compile the project first using: build.bat
    pause
    exit /b 1
)

REM Check if required JARs exist
if not exist "lib\sqlite-jdbc.jar" (
    echo Error: SQLite JDBC driver not found!
    echo Please run build.bat first to download dependencies
    pause
    exit /b 1
)

if not exist "lib\slf4j-api.jar" (
    echo Error: SLF4J API not found!
    echo Please run build.bat first to download dependencies
    pause
    exit /b 1
)

echo Starting application...
echo.

REM Run the application
java -cp "bin;lib\*" com.vitproject.Main

if %errorlevel% neq 0 (
    echo.
    echo Error running application!
    echo Make sure:
    echo   1. Java is installed (java -version)
    echo   2. Project is compiled (run build.bat)
    echo   3. SQLite JDBC driver is in lib\ folder
    pause
)

