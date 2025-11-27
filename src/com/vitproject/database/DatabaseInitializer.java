package com.vitproject.database;

import com.vitproject.util.DatabaseConnection;
import com.vitproject.util.Logger;
import com.vitproject.exception.DatabaseException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Database initialization class
 * Creates tables and initializes database schema
 */
public class DatabaseInitializer {
    
    /**
     * Initialize database and create tables
     */
    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create students table
            String createStudentsTable = """
                CREATE TABLE IF NOT EXISTS students (
                    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    date_of_birth DATE NOT NULL,
                    department TEXT NOT NULL
                )
                """;
            
            // Create courses table
            String createCoursesTable = """
                CREATE TABLE IF NOT EXISTS courses (
                    course_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    course_code TEXT UNIQUE NOT NULL,
                    course_name TEXT NOT NULL,
                    credits INTEGER NOT NULL,
                    instructor TEXT NOT NULL
                )
                """;
            
            // Create grades table
            String createGradesTable = """
                CREATE TABLE IF NOT EXISTS grades (
                    grade_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER NOT NULL,
                    course_id INTEGER NOT NULL,
                    marks REAL NOT NULL,
                    grade TEXT NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
                    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
                    UNIQUE(student_id, course_id)
                )
                """;
            
            stmt.execute(createStudentsTable);
            Logger.info("Students table created/verified");
            
            stmt.execute(createCoursesTable);
            Logger.info("Courses table created/verified");
            
            stmt.execute(createGradesTable);
            Logger.info("Grades table created/verified");
            
            DatabaseConnection.commit();
            Logger.info("Database initialization completed successfully");
            
        } catch (SQLException e) {
            Logger.error("Error initializing database: " + e.getMessage());
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
        } catch (DatabaseException e) {
            Logger.error("Database connection error: " + e.getMessage());
        }
    }
}

