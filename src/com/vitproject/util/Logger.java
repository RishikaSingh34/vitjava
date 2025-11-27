package com.vitproject.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger utility class for logging operations
 * Demonstrates file I/O and exception handling
 */
public class Logger {
    private static final Path LOG_FILE = Paths.get("logs", "application.log");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    static {
        try {
            FileManager.createDirectoryIfNotExists(LOG_FILE.getParent());
        } catch (IOException e) {
            System.err.println("Failed to create log directory: " + e.getMessage());
        }
    }
    
    /**
     * Log info message
     */
    public static void info(String message) {
        log("INFO", message);
    }
    
    /**
     * Log error message
     */
    public static void error(String message) {
        log("ERROR", message);
    }
    
    /**
     * Log warning message
     */
    public static void warning(String message) {
        log("WARNING", message);
    }
    
    /**
     * Log debug message
     */
    public static void debug(String message) {
        log("DEBUG", message);
    }
    
    /**
     * Private method to write log entry
     */
    private static void log(String level, String message) {
        try {
            String logEntry = String.format("[%s] [%s] %s", 
                    LocalDateTime.now().format(FORMATTER), level, message);
            FileManager.appendLine(LOG_FILE, logEntry);
            System.out.println(logEntry); // Also print to console
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}

