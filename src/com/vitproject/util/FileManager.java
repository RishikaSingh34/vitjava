package com.vitproject.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File management utility class
 * Demonstrates NIO.2 API, Path interface, and Stream API
 */
public class FileManager {
    
    /**
     * Read all lines from a file using NIO.2
     */
    public static List<String> readAllLines(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return new ArrayList<>();
        }
        return Files.readAllLines(filePath);
    }
    
    /**
     * Write lines to a file using NIO.2
     */
    public static void writeLines(Path filePath, List<String> lines) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    /**
     * Append line to a file
     */
    public static void appendLine(Path filePath, String line) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, (line + System.lineSeparator()).getBytes(), 
                   StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    
    /**
     * Read file using Stream API
     */
    public static Stream<String> readFileAsStream(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return Stream.empty();
        }
        return Files.lines(filePath);
    }
    
    /**
     * Filter lines using Stream API and lambda expressions
     */
    public static List<String> filterLines(Path filePath, String keyword) throws IOException {
        try (Stream<String> lines = readFileAsStream(filePath)) {
            return lines
                    .filter(line -> line.contains(keyword)) // Lambda expression
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Count lines in file using Stream API
     */
    public static long countLines(Path filePath) throws IOException {
        try (Stream<String> lines = readFileAsStream(filePath)) {
            return lines.count();
        }
    }
    
    /**
     * Check if file exists
     */
    public static boolean fileExists(Path filePath) {
        return Files.exists(filePath);
    }
    
    /**
     * Create directory if it doesn't exist
     */
    public static void createDirectoryIfNotExists(Path dirPath) throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }
    
    /**
     * Delete file
     */
    public static void deleteFile(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
    
    /**
     * Get file size
     */
    public static long getFileSize(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            return Files.size(filePath);
        }
        return 0;
    }
}

