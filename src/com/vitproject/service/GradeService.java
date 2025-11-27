package com.vitproject.service;

import com.vitproject.dao.GradeDAO;
import com.vitproject.model.Grade;
import com.vitproject.exception.CustomException;
import com.vitproject.exception.DatabaseException;
import com.vitproject.util.Logger;

import java.util.List;

/**
 * Service layer for Grade operations
 */
public class GradeService {
    private GradeDAO gradeDAO;
    
    public GradeService() {
        this.gradeDAO = new GradeDAO();
    }
    
    public Grade addGrade(int studentId, int courseId, double marks) throws CustomException {
        try {
            if (marks < 0 || marks > 100) {
                throw new CustomException("Marks must be between 0 and 100", "VALIDATION_ERROR");
            }
            
            Grade grade = new Grade();
            grade.setStudentId(studentId);
            grade.setCourseId(courseId);
            grade.setMarks(marks);
            grade.calculateGrade(); // Method to calculate grade from marks
            
            int id = gradeDAO.create(grade);
            Logger.info("Grade service: Added grade with ID " + id);
            
            return grade;
            
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to add grade: " + e.getMessage(), e);
        }
    }
    
    public List<Grade> getStudentGrades(int studentId) throws CustomException {
        try {
            return gradeDAO.getGradesByStudent(studentId);
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to get student grades: " + e.getMessage(), e);
        }
    }
    
    public List<Grade> getCourseGrades(int courseId) throws CustomException {
        try {
            return gradeDAO.getGradesByCourse(courseId);
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to get course grades: " + e.getMessage(), e);
        }
    }
    
    public boolean updateGrade(Grade grade) throws CustomException {
        try {
            if (grade.getMarks() < 0 || grade.getMarks() > 100) {
                throw new CustomException("Marks must be between 0 and 100", "VALIDATION_ERROR");
            }
            
            grade.calculateGrade();
            return gradeDAO.update(grade);
            
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to update grade: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteGrade(int gradeId) throws CustomException {
        try {
            return gradeDAO.delete(gradeId);
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to delete grade: " + e.getMessage(), e);
        }
    }
}

