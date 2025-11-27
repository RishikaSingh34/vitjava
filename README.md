# Student Management System

## Project Overview

The Student Management System is a comprehensive Java application designed to manage student information, courses, and grades. This project demonstrates various Java programming concepts including OOP principles, JDBC operations, file I/O, multithreading, and collections framework.

## Features

### Core Functionality

1. **Student Management Module**
   - Add, view, update, and delete student records
   - Search students by name
   - View student details and age calculation

2. **Course Management Module**
   - Manage course information (code, name, credits, instructor)
   - Full CRUD operations for courses

3. **Grade Management Module**
   - Record and manage student grades
   - Automatic grade calculation based on marks
   - View grades by student or by course

4. **Reporting Module**
   - Generate detailed student reports
   - Generate summary reports with statistics
   - Export reports to text files

5. **Additional Demonstrations**
   - Collections Framework usage
   - Multithreading with ExecutorService
   - File I/O operations with NIO.2

## Technologies & Tools Used

- **Programming Language**: Java
- **Database**: SQLite (via JDBC)
- **Java Concepts Demonstrated**:
  - Object-Oriented Programming (OOP)
  - Encapsulation, Inheritance, Polymorphism, Abstraction
  - Constructors (Default, Parameterized, Copy)
  - Method Overloading and Overriding
  - Exception Handling (try-catch, custom exceptions)
  - File I/O with NIO.2 API
  - Stream API and Lambda Expressions
  - Collections Framework (ArrayList, LinkedList, Generics)
  - Multithreading (ExecutorService, Runnable)
  - JDBC API for database operations
  - CRUD operations

## Project Structure

```
vitjava/
├── src/
│   └── com/
│       └── vitproject/
│           ├── model/          # Model classes (Student, Course, Grade, Person, Instructor)
│           ├── dao/            # Data Access Objects for database operations
│           ├── service/        # Service layer with business logic
│           ├── exception/      # Custom exception classes
│           ├── util/           # Utility classes (DatabaseConnection, FileManager, Logger)
│           ├── task/           # Multithreading tasks
│           ├── database/       # Database initialization
│           └── Main.java       # Main application entry point
├── logs/                       # Application logs
├── reports/                    # Generated reports
├── student_management.db       # SQLite database file (auto-generated)
├── README.md                   # This file
└── statement.md               # Project statement
```

## Installation & Setup

### Prerequisites

- Java Development Kit (JDBC) 11 or higher
- SQLite JDBC Driver (included via dependency or download separately)

### Steps to Install & Run

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd vitjava
   ```

2. **Download SQLite JDBC Driver**
   - Download `sqlite-jdbc-<version>.jar` from https://github.com/xerial/sqlite-jdbc
   - Place it in a `lib/` directory or add to classpath

3. **Compile the project**
   ```bash
   javac -cp ".:lib/sqlite-jdbc-<version>.jar" -d bin src/com/vitproject/**/*.java
   ```

   Or using wildcard (if supported):
   ```bash
   javac -cp ".:lib/*" -d bin src/com/vitproject/**/*.java
   ```

4. **Run the application**
   ```bash
   java -cp "bin:lib/sqlite-jdbc-<version>.jar" com.vitproject.Main
   ```

   On Windows:
   ```bash
   java -cp "bin;lib\sqlite-jdbc-<version>.jar" com.vitproject.Main
   ```

### Alternative: Using Build Tools

If using Maven or Gradle, add SQLite JDBC dependency:

**Maven (pom.xml)**:
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.42.0.0</version>
</dependency>
```

**Gradle (build.gradle)**:
```gradle
dependencies {
    implementation 'org.xerial:sqlite-jdbc:3.42.0.0'
}
```

## Instructions for Testing

### Manual Testing

1. **Start the application** using the run command above

2. **Test Student Management**:
   - Add a new student (Menu option 1 → 1)
   - View all students (Menu option 1 → 2)
   - Search for a student (Menu option 1 → 6)
   - Update student information (Menu option 1 → 4)
   - Delete a student (Menu option 1 → 5)

3. **Test Course Management**:
   - Add courses (Menu option 2 → 1)
   - View all courses (Menu option 2 → 2)
   - Update/Delete courses (Menu options 2 → 3, 4)

4. **Test Grade Management**:
   - Add grades for students (Menu option 3 → 1)
   - View student grades (Menu option 3 → 2)
   - View course grades (Menu option 3 → 3)

5. **Test Reports**:
   - Generate student report (Menu option 4 → 1)
   - Generate summary report (Menu option 4 → 2)
   - Check the `reports/` directory for generated files

6. **Test Additional Features**:
   - Collections demonstration (Menu option 5)
   - Multithreading demonstration (Menu option 6)
   - File operations demonstration (Menu option 7)

### Sample Test Data

```
Student:
- Name: John Doe
- Email: john.doe@vit.ac.in
- Date of Birth: 2000-05-15
- Department: Computer Science

Course:
- Code: CS101
- Name: Introduction to Programming
- Credits: 3
- Instructor: Dr. Smith

Grade:
- Student ID: 1
- Course ID: 1
- Marks: 85.5
```

## Key Java Concepts Demonstrated

1. **OOP Concepts**:
   - Encapsulation (private fields with getters/setters)
   - Inheritance (Person → Instructor)
   - Polymorphism (method overriding, reference types)
   - Abstraction (abstract Person class)

2. **Constructors**:
   - Default constructor
   - Parameterized constructor
   - Copy constructor

3. **Method Overloading & Overriding**:
   - `calculateAge()` method with different parameters
   - `toString()`, `equals()`, `hashCode()` overrides

4. **Exception Handling**:
   - Try-catch blocks
   - Custom exceptions (CustomException, StudentNotFoundException, DatabaseException)
   - Exception propagation

5. **File I/O**:
   - NIO.2 API (Path, Files)
   - Stream API for file operations
   - Lambda expressions with streams

6. **Collections Framework**:
   - ArrayList, LinkedList
   - Generics
   - Stream operations on collections

7. **Multithreading**:
   - Runnable interface
   - ExecutorService
   - Concurrent task execution

8. **JDBC**:
   - Database connection management
   - PreparedStatement for CRUD operations
   - Transaction management (commit/rollback)

## Screenshots

(Add screenshots of the application running here)

## Database Schema

The application uses SQLite with the following tables:

- **students**: student_id, name, email, date_of_birth, department
- **courses**: course_id, course_code, course_name, credits, instructor
- **grades**: grade_id, student_id, course_id, marks, grade

## Logging

All operations are logged to `logs/application.log` with timestamps and log levels (INFO, ERROR, WARNING, DEBUG).

## Future Enhancements

- Web-based user interface
- RESTful API implementation
- Advanced search and filtering
- Grade analytics and visualization
- Email notifications
- User authentication and authorization
- Export to Excel/PDF formats

## Author

[Your Name]

## License

This project is created for educational purposes as part of VITyarthi course evaluation.

## References

- Java Documentation: https://docs.oracle.com/javase/
- SQLite Documentation: https://www.sqlite.org/docs.html
- SQLite JDBC: https://github.com/xerial/sqlite-jdbc

