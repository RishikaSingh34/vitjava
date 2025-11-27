package com.vitproject.model;

import java.util.Objects;

/**
 * Course model class demonstrating inheritance and abstraction concepts
 */
public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int credits;
    private String instructor;
    
    // Default constructor
    public Course() {
        this.courseId = 0;
        this.courseCode = "";
        this.courseName = "";
        this.credits = 0;
        this.instructor = "";
    }
    
    // Parameterized constructor
    public Course(int courseId, String courseCode, String courseName, int credits, String instructor) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.instructor = instructor;
    }
    
    // Copy constructor
    public Course(Course other) {
        if (other != null) {
            this.courseId = other.courseId;
            this.courseCode = other.courseCode;
            this.courseName = other.courseName;
            this.credits = other.credits;
            this.instructor = other.instructor;
        }
    }
    
    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", instructor='" + instructor + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId == course.courseId &&
                Objects.equals(courseCode, course.courseCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseCode);
    }
}

