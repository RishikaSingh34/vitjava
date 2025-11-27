package com.vitproject.service;

import com.vitproject.dao.CourseDAO;
import com.vitproject.model.Course;
import com.vitproject.exception.CustomException;
import com.vitproject.exception.DatabaseException;
import com.vitproject.util.Logger;

import java.util.List;

/**
 * Service layer for Course operations
 */
public class CourseService {
    private CourseDAO courseDAO;
    
    public CourseService() {
        this.courseDAO = new CourseDAO();
    }
    
    public Course addCourse(String courseCode, String courseName, int credits, String instructor) 
            throws CustomException {
        try {
            if (courseCode == null || courseCode.trim().isEmpty()) {
                throw new CustomException("Course code cannot be empty", "VALIDATION_ERROR");
            }
            
            if (courseName == null || courseName.trim().isEmpty()) {
                throw new CustomException("Course name cannot be empty", "VALIDATION_ERROR");
            }
            
            Course course = new Course();
            course.setCourseCode(courseCode.trim());
            course.setCourseName(courseName.trim());
            course.setCredits(credits);
            course.setInstructor(instructor.trim());
            
            int id = courseDAO.create(course);
            Logger.info("Course service: Added course with ID " + id);
            
            return course;
            
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to add course: " + e.getMessage(), e);
        }
    }
    
    public Course getCourse(int courseId) throws CustomException {
        try {
            return courseDAO.read(courseId);
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to get course: " + e.getMessage(), e);
        }
    }
    
    public List<Course> getAllCourses() throws CustomException {
        try {
            return courseDAO.readAll();
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to get courses: " + e.getMessage(), e);
        }
    }
    
    public boolean updateCourse(Course course) throws CustomException {
        try {
            return courseDAO.update(course);
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to update course: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteCourse(int courseId) throws CustomException {
        try {
            return courseDAO.delete(courseId);
        } catch (DatabaseException e) {
            Logger.error("Service error: " + e.getMessage());
            throw new CustomException("Failed to delete course: " + e.getMessage(), e);
        }
    }
}

