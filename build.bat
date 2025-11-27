@echo off
REM Build script for Student Management System (Windows)

echo Building Student Management System...

REM Create directories
if not exist bin mkdir bin
if not exist lib mkdir lib
if not exist logs mkdir logs
if not exist reports mkdir reports

REM Check if SQLite JDBC driver exists
if not exist "lib\sqlite-jdbc.jar" (
    echo Warning: sqlite-jdbc.jar not found in lib\ directory
    echo Downloading SQLite JDBC driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar' -OutFile 'lib\sqlite-jdbc.jar'"
)

REM Check if SLF4J dependencies exist
if not exist "lib\slf4j-api.jar" (
    echo Downloading SLF4J API...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar' -OutFile 'lib\slf4j-api.jar'"
)

if not exist "lib\slf4j-simple.jar" (
    echo Downloading SLF4J Simple...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar' -OutFile 'lib\slf4j-simple.jar'"
)

REM Compile Java files
echo Compiling Java source files...
javac -cp ".;lib\*" -d bin src\com\vitproject\**\*.java

if %errorlevel% equ 0 (
    echo Build successful!
    echo Run the application with:
    echo   java -cp "bin;lib\*" com.vitproject.Main
) else (
    echo Build failed!
    pause
    exit /b 1
)

pause

