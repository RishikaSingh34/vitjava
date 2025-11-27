package com.vitproject.exception;

/**
 * Exception for database-related errors
 */
public class DatabaseException extends CustomException {
    public DatabaseException(String message) {
        super(message, "DB_ERROR");
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

