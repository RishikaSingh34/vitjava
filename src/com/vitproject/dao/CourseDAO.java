package com.vitproject.dao;

import com.vitproject.model.Course;
import com.vitproject.exception.DatabaseException;
import com.vitproject.util.DatabaseConnection;
import com.vitproject.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Course
 * Demonstrates JDBC CRUD operations
 */
public class CourseDAO {
    
    public int create(Course course) throws DatabaseException {
        String sql = "INSERT INTO courses (course_code, course_name, credits, instructor) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setString(4, course.getInstructor());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        course.setCourseId(generatedId);
                        Logger.info("Course created with ID: " + generatedId);
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
            Logger.error("Error creating course: " + e.getMessage());
            throw new DatabaseException("Failed to create course: " + e.getMessage(), e);
        }
    }
    
    public Course read(int courseId) throws DatabaseException {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCredits(rs.getInt("credits"));
                    course.setInstructor(rs.getString("instructor"));
                    return course;
                }
            }
            
        } catch (SQLException e) {
            Logger.error("Error reading course: " + e.getMessage());
            throw new DatabaseException("Failed to read course: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    public List<Course> readAll() throws DatabaseException {
        String sql = "SELECT * FROM courses ORDER BY course_id";
        List<Course> courses = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setCredits(rs.getInt("credits"));
                course.setInstructor(rs.getString("instructor"));
                courses.add(course);
            }
            
            Logger.info("Retrieved " + courses.size() + " courses");
            return courses;
            
        } catch (SQLException e) {
            Logger.error("Error reading all courses: " + e.getMessage());
            throw new DatabaseException("Failed to read courses: " + e.getMessage(), e);
        }
    }
    
    public boolean update(Course course) throws DatabaseException {
        String sql = "UPDATE courses SET course_code = ?, course_name = ?, credits = ?, instructor = ? WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setString(4, course.getInstructor());
            pstmt.setInt(5, course.getCourseId());
            
            int rowsAffected = pstmt.executeUpdate();
            DatabaseConnection.commit();
            
            if (rowsAffected > 0) {
                Logger.info("Course updated: " + course.getCourseId());
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error updating course: " + e.getMessage());
            throw new DatabaseException("Failed to update course: " + e.getMessage(), e);
        }
    }
    
    public boolean delete(int courseId) throws DatabaseException {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            
            int rowsAffected = pstmt.executeUpdate();
            DatabaseConnection.commit();
            
            if (rowsAffected > 0) {
                Logger.info("Course deleted: " + courseId);
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            try {
                DatabaseConnection.rollback();
            } catch (DatabaseException ex) {
                Logger.error("Failed to rollback: " + ex.getMessage());
            }
            Logger.error("Error deleting course: " + e.getMessage());
            throw new DatabaseException("Failed to delete course: " + e.getMessage(), e);
        }
    }
}

