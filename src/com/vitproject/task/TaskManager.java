package com.vitproject.task;

import com.vitproject.model.Student;
import com.vitproject.util.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Task manager demonstrating ExecutorService for concurrent operations
 * Shows multithreading and concurrent task execution
 */
public class TaskManager {
    private ExecutorService executorService;
    private int threadPoolSize;
    
    public TaskManager(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        Logger.info("TaskManager initialized with thread pool size: " + threadPoolSize);
    }
    
    /**
     * Process students concurrently using ExecutorService
     */
    public void processStudentsConcurrently(List<Student> students) {
        if (students == null || students.isEmpty()) {
            Logger.warning("No students to process");
            return;
        }
        
        // Divide students into chunks for parallel processing
        int chunkSize = Math.max(1, students.size() / threadPoolSize);
        List<List<Student>> chunks = new ArrayList<>();
        
        for (int i = 0; i < students.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, students.size());
            chunks.add(new ArrayList<>(students.subList(i, end)));
        }
        
        Logger.info("Divided " + students.size() + " students into " + chunks.size() + " chunks");
        
        // Submit tasks to executor service
        for (int i = 0; i < chunks.size(); i++) {
            DataProcessor processor = new DataProcessor(chunks.get(i), "Task-" + (i + 1));
            executorService.submit(processor);
        }
        
        Logger.info("Submitted " + chunks.size() + " tasks to executor service");
    }
    
    /**
     * Shutdown executor service gracefully
     */
    public void shutdown() {
        Logger.info("Shutting down TaskManager...");
        executorService.shutdown();
        
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                Logger.warning("Tasks did not complete within timeout. Forcing shutdown...");
                executorService.shutdownNow();
                
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    Logger.error("Executor service did not terminate");
                }
            }
            Logger.info("TaskManager shutdown completed");
        } catch (InterruptedException e) {
            Logger.error("Shutdown interrupted: " + e.getMessage());
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Check if executor is shutdown
     */
    public boolean isShutdown() {
        return executorService.isShutdown();
    }
}

