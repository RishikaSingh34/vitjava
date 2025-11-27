#!/bin/bash

# Quick run script for Linux/Mac

echo "========================================"
echo " Student Management System"
echo "========================================"
echo ""

# Check if bin directory exists
if [ ! -d "bin" ]; then
    echo "Error: bin directory not found!"
    echo "Please compile the project first using: ./build.sh"
    exit 1
fi

# Check if SQLite JDBC driver exists
if [ ! -f "lib/sqlite-jdbc.jar" ] && [ -z "$(ls -A lib/*.jar 2>/dev/null)" ]; then
    echo "Error: SQLite JDBC driver not found in lib/ directory!"
    echo "Please download sqlite-jdbc-*.jar and place it in lib/ folder"
    echo "Download from: https://github.com/xerial/sqlite-jdbc/releases"
    exit 1
fi

echo "Starting application..."
echo ""

# Run the application
java -cp "bin:lib/*" com.vitproject.Main

if [ $? -ne 0 ]; then
    echo ""
    echo "Error running application!"
    echo "Make sure:"
    echo "  1. Java is installed (java -version)"
    echo "  2. Project is compiled (./build.sh)"
    echo "  3. SQLite JDBC driver is in lib/ folder"
fi

