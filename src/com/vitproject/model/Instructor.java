package com.vitproject.model;

/**
 * Instructor class demonstrating inheritance from Person
 * Shows polymorphism and inheritance concepts
 */
public class Instructor extends Person {
    private String department;
    private String employeeId;
    
    // Constructor
    public Instructor(String name, String email, String department, String employeeId) {
        super(name, email); // Calling parent constructor
        this.department = department;
        this.employeeId = employeeId;
    }
    
    // Method overriding - implementing abstract method from Person
    @Override
    public String getDisplayInfo() {
        return "Instructor: " + name + " (" + employeeId + ") - " + department;
    }
    
    // Getters and Setters
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    @Override
    public String toString() {
        return getDisplayInfo();
    }
}

