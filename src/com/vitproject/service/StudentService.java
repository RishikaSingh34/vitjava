package com.vitproject.service;

import com.vitproject.dao.StudentDAO;
import com.vitproject.model.Student;
import com.vitproject.exception.CustomException;
import com.vitproject.exception.DatabaseException;
import com.vitproject.exception.StudentNotFoundException;
import com.vitproject.util.Logger;

import java.util.List;
import java.util.ArrayList;

/**
 * Service layer for Student operations
 * Demonstrates business logic and exception handling
 */
public class StudentService {
    private StudentDAO studentDAO;
    
    public StudentService() {
        this.studentDAO = new StudentDAO();
    }
    
    /**
     * Add new student with validation
     */
    public Student addStudent(String name, String email, String dateOfBirth, String department) 
            throws CustomException {
        try {
            // Validation
            if (name == null || name.trim().isEmpty()) {
                throw new CustomException("Student name cannot be empty", "VALIDATION_ERROR");
            }
            
            if (email == null || !email.contains("@")) {
                throw new CustomException("Invalid email format", "VALIDATION_ERROR");
            }
            
            // Parse date
            java.time.LocalDate dob = java.time.LocalDate.parse(dateOfBirth);
            
            Student student = new Student();
            student.setName(name.trim());
            student.setEmail(email.trim());
            student.setDateOfBirth(dob);
            student.setDepartment(department.trim());
            
            int id = studentDAO.create(student);
            Logger.info("Student service: Added student with ID " + id);
            
            return student;
            
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to add student: " + e.getMessage(), e);
        } catch (Exception e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Invalid input: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get student by ID
     */
    public Student getStudent(int studentId) throws CustomException {
        try {
            return studentDAO.read(studentId);
        } catch (StudentNotFoundException e) {
            Logger.warning("Student not found: " + studentId);
            throw e;
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to get student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get all students
     */
    public List<Student> getAllStudents() throws CustomException {
        try {
            return studentDAO.readAll();
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to get students: " + e.getMessage(), e);
        }
    }
    
    /**
     * Update student
     */
    public boolean updateStudent(Student student) throws CustomException {
        try {
            return studentDAO.update(student);
        } catch (StudentNotFoundException e) {
            Logger.warning("Student not found for update: " + student.getStudentId());
            throw e;
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to update student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Delete student
     */
    public boolean deleteStudent(int studentId) throws CustomException {
        try {
            return studentDAO.delete(studentId);
        } catch (StudentNotFoundException e) {
            Logger.warning("Student not found for deletion: " + studentId);
            throw e;
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to delete student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Search students by name
     */
    public List<Student> searchStudents(String name) throws CustomException {
        try {
            if (name == null || name.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return studentDAO.searchByName(name.trim());
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to search students: " + e.getMessage(), e);
        }
    }
}

