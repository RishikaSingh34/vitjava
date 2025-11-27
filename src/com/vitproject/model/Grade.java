package com.vitproject.model;

import java.util.Objects;

/**
 * Grade model class representing student-course grade relationship
 */
public class Grade {
    private int gradeId;
    private int studentId;
    private int courseId;
    private double marks;
    private String grade;
    
    // Default constructor
    public Grade() {
        this.gradeId = 0;
        this.studentId = 0;
        this.courseId = 0;
        this.marks = 0.0;
        this.grade = "";
    }
    
    // Parameterized constructor
    public Grade(int gradeId, int studentId, int courseId, double marks, String grade) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
        this.grade = grade;
    }
    
    // Copy constructor
    public Grade(Grade other) {
        if (other != null) {
            this.gradeId = other.gradeId;
            this.studentId = other.studentId;
            this.courseId = other.courseId;
            this.marks = other.marks;
            this.grade = other.grade;
        }
    }
    
    // Getters and Setters
    public int getGradeId() {
        return gradeId;
    }
    
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public double getMarks() {
        return marks;
    }
    
    public void setMarks(double marks) {
        this.marks = marks;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    // Method to calculate grade from marks
    public void calculateGrade() {
        if (marks >= 90) {
            this.grade = "A+";
        } else if (marks >= 80) {
            this.grade = "A";
        } else if (marks >= 70) {
            this.grade = "B";
        } else if (marks >= 60) {
            this.grade = "C";
        } else if (marks >= 50) {
            this.grade = "D";
        } else {
            this.grade = "F";
        }
    }
    
    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", marks=" + marks +
                ", grade='" + grade + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grade grade1 = (Grade) obj;
        return gradeId == grade1.gradeId &&
                studentId == grade1.studentId &&
                courseId == grade1.courseId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gradeId, studentId, courseId);
    }
}

