package com.vitproject.dao;

import com.vitproject.model.Grade;
import com.vitproject.exception.DatabaseException;
import com.vitproject.util.DatabaseConnection;
import com.vitproject.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Grade
 */
public class GradeDAO {
    
    public int create(Grade grade) throws DatabaseException {
        String sql = "INSERT INTO grades (student_id, course_id, marks, grade) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setDouble(3, grade.getMarks());
            pstmt.setString(4, grade.getGrade());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        grade.setGradeId(generatedId);
                        Logger.info("Grade created with ID: " + generatedId);
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
            Logger.error("Error creating grade: " + e.getMessage());
            throw new DatabaseException("Failed to create grade: " + e.getMessage(), e);
        }
    }
    
    public List<Grade> getGradesByStudent(int studentId) throws DatabaseException {
        String sql = "SELECT * FROM grades WHERE student_id = ?";
        List<Grade> grades = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Grade grade = new Grade();
                    grade.setGradeId(rs.getInt("grade_id"));
                    grade.setStudentId(rs.getInt("student_id"));
                    grade.setCourseId(rs.getInt("course_id"));
                    grade.setMarks(rs.getDouble("marks"));
                    grade.setGrade(rs.getString("grade"));
                    grades.add(grade);
                }
            }
            
            return grades;
            
        } catch (SQLException e) {
            Logger.error("Error reading grades: " + e.getMessage());
            throw new DatabaseException("Failed to read grades: " + e.getMessage(), e);
        }
    }
    
    public List<Grade> getGradesByCourse(int courseId) throws DatabaseException {
        String sql = "SELECT * FROM grades WHERE course_id = ?";
        List<Grade> grades = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Grade grade = new Grade();
                    grade.setGradeId(rs.getInt("grade_id"));
                    grade.setStudentId(rs.getInt("student_id"));
                    grade.setCourseId(rs.getInt("course_id"));
                    grade.setMarks(rs.getDouble("marks"));
                    grade.setGrade(rs.getString("grade"));
                    grades.add(grade);
                }
            }
            
            return grades;
            
        } catch (SQLException e) {
            Logger.error("Error reading grades: " + e.getMessage());
            throw new DatabaseException("Failed to read grades: " + e.getMessage(), e);
        }
    }
    
    public boolean update(Grade grade) throws DatabaseException {
        String sql = "UPDATE grades SET marks = ?, grade = ? WHERE grade_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, grade.getMarks());
            pstmt.setString(2, grade.getGrade());
            pstmt.setInt(3, grade.getGradeId());
            
            int rowsAffected = pstmt.executeUpdate();
            DatabaseConnection.commit();
            
            if (rowsAffected > 0) {
                Logger.info("Grade updated: " + grade.getGradeId());
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error updating grade: " + e.getMessage());
            throw new DatabaseException("Failed to update grade: " + e.getMessage(), e);
        }
    }
    
    public boolean delete(int gradeId) throws DatabaseException {
        String sql = "DELETE FROM grades WHERE grade_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gradeId);
            
            int rowsAffected = pstmt.executeUpdate();
            DatabaseConnection.commit();
            
            if (rowsAffected > 0) {
                Logger.info("Grade deleted: " + gradeId);
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error deleting grade: " + e.getMessage());
            throw new DatabaseException("Failed to delete grade: " + e.getMessage(), e);
        }
    }
}

