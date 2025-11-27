package com.vitproject.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Student model class demonstrating OOP concepts:
 * - Encapsulation (private fields with getters/setters)
 * - Constructor overloading
 * - Copy constructor
 * - Method overriding (equals, hashCode, toString)
 */
public class Student {
    // Encapsulation: private fields
    private int studentId;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String department;
    
    // Default constructor
    public Student() {
        this.studentId = 0;
        this.name = "";
        this.email = "";
        this.dateOfBirth = LocalDate.now();
        this.department = "";
    }
    
    // Parameterized constructor
    public Student(int studentId, String name, String email, LocalDate dateOfBirth, String department) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
    }
    
    // Copy constructor
    public Student(Student other) {
        if (other != null) {
            this.studentId = other.studentId;
            this.name = other.name;
            this.email = other.email;
            this.dateOfBirth = other.dateOfBirth;
            this.department = other.department;
        }
    }
    
    // Getters and Setters (Encapsulation)
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Method overriding: toString
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", department='" + department + '\'' +
                '}';
    }
    
    // Method overriding: equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId == student.studentId &&
                Objects.equals(email, student.email);
    }
    
    // Method overriding: hashCode
    @Override
    public int hashCode() {
        return Objects.hash(studentId, email);
    }
    
    // Method overloading: calculateAge with different parameters
    public int calculateAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public int calculateAge(LocalDate referenceDate) {
        return referenceDate.getYear() - dateOfBirth.getYear();
    }
}

