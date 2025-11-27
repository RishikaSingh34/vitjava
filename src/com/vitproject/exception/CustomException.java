package com.vitproject.exception;

/**
 * Custom exception class demonstrating exception handling
 */
public class CustomException extends Exception {
    private String errorCode;
    
    // Default constructor
    public CustomException(String message) {
        super(message);
        this.errorCode = "GEN_ERR";
    }
    
    // Constructor with error code
    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    // Constructor with cause
    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "GEN_ERR";
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String toString() {
        return "CustomException{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}

