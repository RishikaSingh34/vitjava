package com.vitproject.util;

import com.vitproject.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class
 * Demonstrates JDBC API usage and resource management
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:student_management.db";
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation (Singleton pattern)
    private DatabaseConnection() {}
    
    /**
     * Get database connection
     * Demonstrates exception handling with try-catch
     */
    public static Connection getConnection() throws DatabaseException {
        try {
            if (connection == null || connection.isClosed()) {
                // Load SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                connection.setAutoCommit(false); // For transaction management
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("JDBC Driver not found: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to connect to database: " + e.getMessage(), e);
        }
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Commit transaction
     */
    public static void commit() throws DatabaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to commit transaction: " + e.getMessage(), e);
        }
    }
    
    /**
     * Rollback transaction
     */
    public static void rollback() throws DatabaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to rollback transaction: " + e.getMessage(), e);
        }
    }
}

