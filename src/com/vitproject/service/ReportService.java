package com.vitproject.service;

import com.vitproject.dao.StudentDAO;
import com.vitproject.dao.CourseDAO;
import com.vitproject.dao.GradeDAO;
import com.vitproject.model.Student;
import com.vitproject.model.Course;
import com.vitproject.model.Grade;
import com.vitproject.exception.CustomException;
import com.vitproject.util.FileManager;
import com.vitproject.util.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Report service demonstrating Stream API, lambdas, and file operations
 */
public class ReportService {
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private GradeDAO gradeDAO;
    private static final Path REPORT_DIR = Paths.get("reports");
    
    public ReportService() {
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
        this.gradeDAO = new GradeDAO();
        
        try {
            FileManager.createDirectoryIfNotExists(REPORT_DIR);
        } catch (Exception e) {
            Logger.error("Failed to create report directory: " + e.getMessage());
        }
    }
    
    /**
     * Generate student report using Stream API and lambdas
     */
    public void generateStudentReport(int studentId) throws CustomException {
        try {
            Student student = studentDAO.read(studentId);
            List<Grade> grades = gradeDAO.getGradesByStudent(studentId);
            
            List<String> reportLines = new ArrayList<>();
            reportLines.add("=".repeat(50));
            reportLines.add("STUDENT REPORT");
            reportLines.add("=".repeat(50));
            reportLines.add("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            reportLines.add("");
            reportLines.add("Student ID: " + student.getStudentId());
            reportLines.add("Name: " + student.getName());
            reportLines.add("Email: " + student.getEmail());
            reportLines.add("Department: " + student.getDepartment());
            reportLines.add("Age: " + student.calculateAge());
            reportLines.add("");
            reportLines.add("Grades:");
            reportLines.add("-".repeat(50));
            
            // Using Stream API and lambdas to process grades
            List<String> gradeLines = grades.stream()
                    .map(grade -> {
                        try {
                            Course course = courseDAO.read(grade.getCourseId());
                            return String.format("  %s (%s): %.2f - %s", 
                                    course.getCourseName(), 
                                    course.getCourseCode(), 
                                    grade.getMarks(), 
                                    grade.getGrade());
                        } catch (Exception e) {
                            return "  Course ID " + grade.getCourseId() + ": " + grade.getMarks() + " - " + grade.getGrade();
                        }
                    })
                    .collect(Collectors.toList());
            
            reportLines.addAll(gradeLines);
            
            // Calculate average using Stream API
            double average = grades.stream()
                    .mapToDouble(Grade::getMarks)
                    .average()
                    .orElse(0.0);
            
            reportLines.add("");
            reportLines.add("Average Marks: " + String.format("%.2f", average));
            reportLines.add("=".repeat(50));
            
            // Write to file
            Path reportFile = REPORT_DIR.resolve("student_" + studentId + "_" + 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt");
            FileManager.writeLines(reportFile, reportLines);
            
            Logger.info("Student report generated: " + reportFile);
            
        } catch (Exception e) {
            Logger.error("Error generating student report: " + e.getMessage());
            throw new CustomException("Failed to generate report: " + e.getMessage(), e);
        }
    }
    
    /**
     * Generate summary report using Collections and Stream API
     */
    public void generateSummaryReport() throws CustomException {
        try {
            List<Student> students = studentDAO.readAll();
            List<Course> courses = courseDAO.readAll();
            List<Grade> allGrades = new ArrayList<>();
            
            // Collect all grades
            for (Student student : students) {
                allGrades.addAll(gradeDAO.getGradesByStudent(student.getStudentId()));
            }
            
            List<String> reportLines = new ArrayList<>();
            reportLines.add("=".repeat(50));
            reportLines.add("SUMMARY REPORT");
            reportLines.add("=".repeat(50));
            reportLines.add("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            reportLines.add("");
            reportLines.add("Total Students: " + students.size());
            reportLines.add("Total Courses: " + courses.size());
            reportLines.add("Total Grades Recorded: " + allGrades.size());
            reportLines.add("");
            
            // Using Stream API to calculate statistics
            if (!allGrades.isEmpty()) {
                double avgMarks = allGrades.stream()
                        .mapToDouble(Grade::getMarks)
                        .average()
                        .orElse(0.0);
                
                double maxMarks = allGrades.stream()
                        .mapToDouble(Grade::getMarks)
                        .max()
                        .orElse(0.0);
                
                double minMarks = allGrades.stream()
                        .mapToDouble(Grade::getMarks)
                        .min()
                        .orElse(0.0);
                
                reportLines.add("Statistics:");
                reportLines.add("  Average Marks: " + String.format("%.2f", avgMarks));
                reportLines.add("  Maximum Marks: " + String.format("%.2f", maxMarks));
                reportLines.add("  Minimum Marks: " + String.format("%.2f", minMarks));
            }
            
            reportLines.add("=".repeat(50));
            
            Path reportFile = REPORT_DIR.resolve("summary_" + 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt");
            FileManager.writeLines(reportFile, reportLines);
            
            Logger.info("Summary report generated: " + reportFile);
            
        } catch (Exception e) {
            Logger.error("Error generating summary report: " + e.getMessage());
            throw new CustomException("Failed to generate summary report: " + e.getMessage(), e);
        }
    }
}

