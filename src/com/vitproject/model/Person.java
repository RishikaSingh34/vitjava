package com.vitproject.model;


public abstract class Person {
    protected String name;
    protected String email;
    
    // Constructor
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getDisplayInfo();
    
    // Concrete method
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
}

