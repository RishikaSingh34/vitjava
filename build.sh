#!/bin/bash

# Build script for Student Management System

echo "Building Student Management System..."

# Create directories
mkdir -p bin
mkdir -p lib
mkdir -p logs
mkdir -p reports

# Check if SQLite JDBC driver exists
if [ ! -f "lib/sqlite-jdbc.jar" ]; then
    echo "Warning: sqlite-jdbc.jar not found in lib/ directory"
    echo "Please download SQLite JDBC driver and place it in lib/ directory"
    echo "Download from: https://github.com/xerial/sqlite-jdbc/releases"
    exit 1
fi

# Compile Java files
echo "Compiling Java source files..."
javac -cp ".:lib/*" -d bin src/com/vitproject/**/*.java

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo "Run the application with:"
    echo "  java -cp \"bin:lib/*\" com.vitproject.Main"
else
    echo "Build failed!"
    exit 1
fi

