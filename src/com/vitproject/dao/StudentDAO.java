package com.vitproject.dao;

import com.vitproject.model.Student;
import com.vitproject.exception.DatabaseException;
import com.vitproject.exception.StudentNotFoundException;
import com.vitproject.util.DatabaseConnection;
import com.vitproject.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student
 * Demonstrates JDBC API and CRUD operations
 */
public class StudentDAO {
    
    /**
     * Create (Insert) operation
     */
    public int create(Student student) throws DatabaseException {
        String sql = "INSERT INTO students (name, email, date_of_birth, department) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setDate(3, Date.valueOf(student.getDateOfBirth()));
            pstmt.setString(4, student.getDepartment());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        student.setStudentId(generatedId);
                        Logger.info("Student created with ID: " + generatedId);
                        DatabaseConnection.commit();
                        return generatedId;
                    }
                }
            }
            
            DatabaseConnection.commit();
            return rowsAffected;
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error creating student: " + e.getMessage());
            throw new DatabaseException("Failed to create student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Read (Select) operation - Get by ID
     */
    public Student read(int studentId) throws DatabaseException, StudentNotFoundException {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                    student.setDepartment(rs.getString("department"));
                    Logger.info("Student retrieved: " + studentId);
                    return student;
                } else {
                    throw new StudentNotFoundException(studentId);
                }
            }
            
        } catch (SQLException e) {
            Logger.error("Error reading student: " + e.getMessage());
            throw new DatabaseException("Failed to read student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Read all students
     */
    public List<Student> readAll() throws DatabaseException {
        String sql = "SELECT * FROM students ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                student.setDepartment(rs.getString("department"));
                students.add(student);
            }
            
            Logger.info("Retrieved " + students.size() + " students");
            return students;
            
        } catch (SQLException e) {
            Logger.error("Error reading all students: " + e.getMessage());
            throw new DatabaseException("Failed to read students: " + e.getMessage(), e);
        }
    }
    
    /**
     * Update operation
     */
    public boolean update(Student student) throws DatabaseException, StudentNotFoundException {
        String sql = "UPDATE students SET name = ?, email = ?, date_of_birth = ?, department = ? WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setDate(3, Date.valueOf(student.getDateOfBirth()));
            pstmt.setString(4, student.getDepartment());
            pstmt.setInt(5, student.getStudentId());
            
            int rowsAffected = pstmt.executeUpdate();
            DatabaseConnection.commit();
            
            if (rowsAffected > 0) {
                Logger.info("Student updated: " + student.getStudentId());
                return true;
            } else {
                throw new StudentNotFoundException(student.getStudentId());
            }
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error updating student: " + e.getMessage());
            throw new DatabaseException("Failed to update student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Delete operation
     */
    public boolean delete(int studentId) throws DatabaseException, StudentNotFoundException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            DatabaseConnection.commit();
            
            if (rowsAffected > 0) {
                Logger.info("Student deleted: " + studentId);
                return true;
            } else {
                throw new StudentNotFoundException(studentId);
            }
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error deleting student: " + e.getMessage());
            throw new DatabaseException("Failed to delete student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Search students by name
     */
    public List<Student> searchByName(String name) throws DatabaseException {
        String sql = "SELECT * FROM students WHERE name LIKE ? ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                    student.setDepartment(rs.getString("department"));
                    students.add(student);
                }
            }
            
            Logger.info("Found " + students.size() + " students matching: " + name);
            return students;
            
        } catch (SQLException e) {
            Logger.error("Error searching students: " + e.getMessage());
            throw new DatabaseException("Failed to search students: " + e.getMessage(), e);
        }
    }
}

