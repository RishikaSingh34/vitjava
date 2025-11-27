package com.vitproject.exception;

/**
 * Specific exception for student not found scenarios
 */
public class StudentNotFoundException extends CustomException {
    public StudentNotFoundException(int studentId) {
        super("Student with ID " + studentId + " not found", "STU_NOT_FOUND");
    }
    
    public StudentNotFoundException(String message) {
        super(message, "STU_NOT_FOUND");
    }
}

