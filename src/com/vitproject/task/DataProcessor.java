package com.vitproject.task;

import com.vitproject.model.Student;
import com.vitproject.util.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Data processing task demonstrating multithreading concepts
 * This class implements Runnable for concurrent execution
 */
public class DataProcessor implements Runnable {
    private List<Student> students;
    private String taskName;
    
    public DataProcessor(List<Student> students, String taskName) {
        this.students = new ArrayList<>(students); // Copy constructor usage
        this.taskName = taskName;
    }
    
    @Override
    public void run() {
        try {
            Logger.info("Task '" + taskName + "' started. Processing " + students.size() + " students.");
            
            // Simulate data processing using Stream API
            List<String> studentNames = students.stream()
                    .map(Student::getName)
                    .filter(name -> name != null && !name.isEmpty())
                    .sorted()
                    .collect(Collectors.toList());
            
            // Simulate some processing time
            Thread.sleep(1000);
            
            Logger.info("Task '" + taskName + "' completed. Processed " + studentNames.size() + " student names.");
            
        } catch (InterruptedException e) {
            Logger.error("Task '" + taskName + "' interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            Logger.error("Error in task '" + taskName + "': " + e.getMessage());
        }
    }
}

