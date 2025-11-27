# How to Run the Student Management System

## Prerequisites

1. **Java Development Kit (JDK) 11 or higher**
   - Check if installed: `java -version` and `javac -version`
   - Download from: https://www.oracle.com/java/technologies/downloads/

2. **SQLite JDBC Driver**
   - Download from: https://github.com/xerial/sqlite-jdbc/releases
   - Get the latest version (e.g., `sqlite-jdbc-3.42.0.0.jar`)

## Step-by-Step Setup

### Step 1: Download SQLite JDBC Driver

1. Go to https://github.com/xerial/sqlite-jdbc/releases
2. Download the latest `sqlite-jdbc-<version>.jar` file
3. Create a `lib` folder in your project root directory
4. Place the downloaded JAR file in the `lib` folder

Your project structure should look like:
```
vitjava/
├── src/
├── lib/
│   └── sqlite-jdbc-3.42.0.0.jar  (or your version)
├── README.md
└── ...
```

### Step 2: Create Required Directories

The application will create these automatically, but you can create them manually:

```bash
# Windows (PowerShell)
mkdir bin, logs, reports

# Windows (Command Prompt)
mkdir bin
mkdir logs
mkdir reports

# Linux/Mac
mkdir -p bin logs reports
```

### Step 3: Compile the Project

#### Option A: Using Command Line (Manual)

**Windows:**
```cmd
javac -cp ".;lib\sqlite-jdbc-3.42.0.0.jar" -d bin src\com\vitproject\model\*.java src\com\vitproject\dao\*.java src\com\vitproject\service\*.java src\com\vitproject\exception\*.java src\com\vitproject\util\*.java src\com\vitproject\task\*.java src\com\vitproject\database\*.java src\com\vitproject\Main.java
```

**Linux/Mac:**
```bash
javac -cp ".:lib/sqlite-jdbc-3.42.0.0.jar" -d bin src/com/vitproject/**/*.java
```

**Alternative (if wildcard doesn't work):**
```bash
# Windows
javac -cp ".;lib\*" -d bin -sourcepath src src/com/vitproject/**/*.java

# Linux/Mac
javac -cp ".:lib/*" -d bin -sourcepath src src/com/vitproject/**/*.java
```

#### Option B: Using Build Script

**Windows:**
```cmd
build.bat
```

**Linux/Mac:**
```bash
chmod +x build.sh
./build.sh
```

### Step 4: Run the Application

**Windows:**
```cmd
java -cp "bin;lib\sqlite-jdbc-3.42.0.0.jar" com.vitproject.Main
```

Or using wildcard:
```cmd
java -cp "bin;lib\*" com.vitproject.Main
```

**Linux/Mac:**
```bash
java -cp "bin:lib/sqlite-jdbc-3.42.0.0.jar" com.vitproject.Main
```

Or using wildcard:
```bash
java -cp "bin:lib/*" com.vitproject.Main
```

## Quick Start Script

### For Windows (run.bat)

Create a file named `run.bat`:

```batch
@echo off
echo Starting Student Management System...
java -cp "bin;lib\*" com.vitproject.Main
pause
```

### For Linux/Mac (run.sh)

Create a file named `run.sh`:

```bash
#!/bin/bash
echo "Starting Student Management System..."
java -cp "bin:lib/*" com.vitproject.Main
```

Make it executable:
```bash
chmod +x run.sh
./run.sh
```

## Troubleshooting

### Error: "Could not find or load main class com.vitproject.Main"

**Solution:**
- Make sure you compiled the code (Step 3)
- Check that `bin/com/vitproject/Main.class` exists
- Verify the classpath includes the `bin` directory

### Error: "java.lang.ClassNotFoundException: org.sqlite.JDBC"

**Solution:**
- Make sure SQLite JDBC JAR is in the `lib` folder
- Check the classpath includes `lib\*` or `lib/sqlite-jdbc-*.jar`
- Verify the JAR file name matches what's in your classpath

### Error: "javac: file not found"

**Solution:**
- Make sure you're in the project root directory
- Check that source files exist in `src/com/vitproject/`
- Try compiling individual packages first

### Database Connection Issues

**Solution:**
- The database file `student_management.db` will be created automatically
- Make sure you have write permissions in the project directory
- Check logs/application.log for detailed error messages

## Using an IDE (Eclipse/IntelliJ IDEA)

### Eclipse:
1. File → New → Java Project
2. Import existing source files
3. Add SQLite JDBC JAR to Build Path:
   - Right-click project → Properties → Java Build Path → Libraries
   - Add External JARs → Select sqlite-jdbc-*.jar
4. Run → Run As → Java Application → Select Main class

### IntelliJ IDEA:
1. File → Open → Select project folder
2. File → Project Structure → Libraries
3. Click + → Java → Select sqlite-jdbc-*.jar
4. Right-click Main.java → Run 'Main.main()'

## First Run

When you first run the application:

1. The database will be automatically initialized
2. You'll see the main menu
3. Start by adding a student (Option 1 → 1)
4. Then add a course (Option 2 → 1)
5. Add a grade (Option 3 → 1)

## Sample Test Flow

```
1. Add Student:
   - Name: John Doe
   - Email: john.doe@vit.ac.in
   - Date of Birth: 2000-05-15
   - Department: Computer Science

2. Add Course:
   - Code: CS101
   - Name: Introduction to Programming
   - Credits: 3
   - Instructor: Dr. Smith

3. Add Grade:
   - Student ID: 1
   - Course ID: 1
   - Marks: 85.5

4. Generate Report:
   - Option 4 → 1 → Enter Student ID: 1
   - Check reports/ folder for generated report
```

## Expected Output

When running successfully, you should see:

```
[2024-01-01 10:00:00] [INFO] === Student Management System Started ===
[2024-01-01 10:00:00] [INFO] Students table created/verified
[2024-01-01 10:00:00] [INFO] Courses table created/verified
[2024-01-01 10:00:00] [INFO] Grades table created/verified
[2024-01-01 10:00:00] [INFO] Database initialization completed successfully

--- Polymorphism Demonstration ---
Polymorphism example - Person references:
Instructor: Dr. Smith (EMP001) - Computer Science
Instructor: Dr. Jones (EMP002) - Mathematics

==================================================
    STUDENT MANAGEMENT SYSTEM
==================================================
1. Student Management
2. Course Management
3. Grade Management
4. Reports
5. Demonstrate Collections
6. Demonstrate Multithreading
7. Demonstrate File Operations
0. Exit
==================================================
Enter your choice:
```

## Notes

- The database file (`student_management.db`) will be created in the project root
- Logs are saved in `logs/application.log`
- Reports are saved in `reports/` directory
- All operations are logged with timestamps

