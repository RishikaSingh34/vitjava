package com.vitproject;

import com.vitproject.database.DatabaseInitializer;
import com.vitproject.model.Student;
import com.vitproject.model.Course;
import com.vitproject.model.Grade;
import com.vitproject.model.Instructor;
import com.vitproject.service.StudentService;
import com.vitproject.service.CourseService;
import com.vitproject.service.GradeService;
import com.vitproject.service.ReportService;
import com.vitproject.task.TaskManager;
import com.vitproject.exception.CustomException;
import com.vitproject.util.DatabaseConnection;
import com.vitproject.util.Logger;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main application class
 * Demonstrates all Java concepts covered in the course
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService;
    private static CourseService courseService;
    private static GradeService gradeService;
    private static ReportService reportService;
    
    public static void main(String[] args) {
        Logger.info("=== Student Management System Started ===");
        
        try {
            // Initialize database
            DatabaseInitializer.initialize();
            
            // Initialize services
            studentService = new StudentService();
            courseService = new CourseService();
            gradeService = new GradeService();
            reportService = new ReportService();
            
            // Demonstrate polymorphism with Person/Instructor
            demonstratePolymorphism();
            
            // Main menu loop
            boolean running = true;
            while (running) {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        studentMenu();
                        break;
                    case 2:
                        courseMenu();
                        break;
                    case 3:
                        gradeMenu();
                        break;
                    case 4:
                        reportMenu();
                        break;
                    case 5:
                        demonstrateCollections();
                        break;
                    case 6:
                        demonstrateMultithreading();
                        break;
                    case 7:
                        demonstrateFileOperations();
                        break;
                    case 0:
                        running = false;
                        Logger.info("Application shutting down...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
        } catch (Exception e) {
            Logger.error("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cleanup
            DatabaseConnection.closeConnection();
            scanner.close();
            Logger.info("=== Student Management System Terminated ===");
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    STUDENT MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Grade Management");
        System.out.println("4. Reports");
        System.out.println("5. Demonstrate Collections");
        System.out.println("6. Demonstrate Multithreading");
        System.out.println("7. Demonstrate File Operations");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }
    
    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Search Students");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        viewStudentById();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        searchStudents();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void addStudent() throws CustomException {
        System.out.println("\n--- Add New Student ---");
        String name = getStringInput("Enter name: ");
        String email = getStringInput("Enter email: ");
        String dob = getStringInput("Enter date of birth (YYYY-MM-DD): ");
        String department = getStringInput("Enter department: ");
        
        Student student = studentService.addStudent(name, email, dob, department);
        System.out.println("Student added successfully!");
        System.out.println("Student ID: " + student.getStudentId());
    }
    
    private static void viewAllStudents() throws CustomException {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.printf("%-10s %-20s %-30s %-15s %-20s%n", 
                    "ID", "Name", "Email", "Date of Birth", "Department");
            System.out.println("-".repeat(95));
            for (Student student : students) {
                System.out.printf("%-10d %-20s %-30s %-15s %-20s%n",
                        student.getStudentId(),
                        student.getName(),
                        student.getEmail(),
                        student.getDateOfBirth(),
                        student.getDepartment());
            }
        }
    }
    
    private static void viewStudentById() throws CustomException {
        int id = getIntInput("Enter student ID: ");
        Student student = studentService.getStudent(id);
        System.out.println("\n--- Student Details ---");
        System.out.println(student);
        System.out.println("Age: " + student.calculateAge());
    }
    
    private static void updateStudent() throws CustomException {
        int id = getIntInput("Enter student ID to update: ");
        Student student = studentService.getStudent(id);
        
        System.out.println("Current details: " + student);
        System.out.println("Enter new details (press Enter to keep current value):");
        
        String name = getStringInput("Name [" + student.getName() + "]: ");
        if (!name.isEmpty()) student.setName(name);
        
        String email = getStringInput("Email [" + student.getEmail() + "]: ");
        if (!email.isEmpty()) student.setEmail(email);
        
        String dept = getStringInput("Department [" + student.getDepartment() + "]: ");
        if (!dept.isEmpty()) student.setDepartment(dept);
        
        if (studentService.updateStudent(student)) {
            System.out.println("Student updated successfully!");
        }
    }
    
    private static void deleteStudent() throws CustomException {
        int id = getIntInput("Enter student ID to delete: ");
        if (studentService.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        }
    }
    
    private static void searchStudents() throws CustomException {
        String name = getStringInput("Enter name to search: ");
        List<Student> students = studentService.searchStudents(name);
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
    
    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        viewAllCourses();
                        break;
                    case 3:
                        updateCourse();
                        break;
                    case 4:
                        deleteCourse();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void addCourse() throws CustomException {
        System.out.println("\n--- Add New Course ---");
        String code = getStringInput("Enter course code: ");
        String name = getStringInput("Enter course name: ");
        int credits = getIntInput("Enter credits: ");
        String instructor = getStringInput("Enter instructor name: ");
        
        Course course = courseService.addCourse(code, name, credits, instructor);
        System.out.println("Course added successfully! Course ID: " + course.getCourseId());
    }
    
    private static void viewAllCourses() throws CustomException {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.printf("%-10s %-15s %-30s %-10s %-20s%n",
                    "ID", "Code", "Name", "Credits", "Instructor");
            System.out.println("-".repeat(95));
            for (Course course : courses) {
                System.out.printf("%-10d %-15s %-30s %-10d %-20s%n",
                        course.getCourseId(),
                        course.getCourseCode(),
                        course.getCourseName(),
                        course.getCredits(),
                        course.getInstructor());
            }
        }
    }
    
    private static void updateCourse() throws CustomException {
        int id = getIntInput("Enter course ID to update: ");
        Course course = courseService.getCourse(id);
        
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        
        System.out.println("Current details: " + course);
        String name = getStringInput("Enter new course name: ");
        if (!name.isEmpty()) course.setCourseName(name);
        
        if (courseService.updateCourse(course)) {
            System.out.println("Course updated successfully!");
        }
    }
    
    private static void deleteCourse() throws CustomException {
        int id = getIntInput("Enter course ID to delete: ");
        if (courseService.deleteCourse(id)) {
            System.out.println("Course deleted successfully!");
        }
    }
    
    private static void gradeMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Grade Management ---");
            System.out.println("1. Add Grade");
            System.out.println("2. View Student Grades");
            System.out.println("3. View Course Grades");
            System.out.println("4. Update Grade");
            System.out.println("5. Delete Grade");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addGrade();
                        break;
                    case 2:
                        viewStudentGrades();
                        break;
                    case 3:
                        viewCourseGrades();
                        break;
                    case 4:
                        updateGrade();
                        break;
                    case 5:
                        deleteGrade();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void addGrade() throws CustomException {
        System.out.println("\n--- Add Grade ---");
        int studentId = getIntInput("Enter student ID: ");
        int courseId = getIntInput("Enter course ID: ");
        double marks = getDoubleInput("Enter marks (0-100): ");
        
        Grade grade = gradeService.addGrade(studentId, courseId, marks);
        System.out.println("Grade added successfully! Grade: " + grade.getGrade());
    }
    
    private static void viewStudentGrades() throws CustomException {
        int studentId = getIntInput("Enter student ID: ");
        List<Grade> grades = gradeService.getStudentGrades(studentId);
        
        if (grades.isEmpty()) {
            System.out.println("No grades found for this student.");
        } else {
            System.out.println("\n--- Student Grades ---");
            for (Grade grade : grades) {
                System.out.println("Course ID: " + grade.getCourseId() + 
                        ", Marks: " + grade.getMarks() + ", Grade: " + grade.getGrade());
            }
        }
    }
    
    private static void viewCourseGrades() throws CustomException {
        int courseId = getIntInput("Enter course ID: ");
        List<Grade> grades = gradeService.getCourseGrades(courseId);
        
        if (grades.isEmpty()) {
            System.out.println("No grades found for this course.");
        } else {
            System.out.println("\n--- Course Grades ---");
            for (Grade grade : grades) {
                System.out.println("Student ID: " + grade.getStudentId() + 
                        ", Marks: " + grade.getMarks() + ", Grade: " + grade.getGrade());
            }
        }
    }
    
    private static void updateGrade() throws CustomException {
        int gradeId = getIntInput("Enter grade ID to update: ");
        double marks = getDoubleInput("Enter new marks: ");
        
        // Note: This is a simplified implementation
        // In a full implementation, you would:
        // 1. Get the grade by ID from database
        // 2. Update the marks
        // 3. Recalculate the grade
        // 4. Save back to database
        
        System.out.println("Note: Grade update functionality requires additional DAO method (getGradeById).");
        System.out.println("Grade ID: " + gradeId + ", New Marks: " + marks);
    }
    
    private static void deleteGrade() throws CustomException {
        int gradeId = getIntInput("Enter grade ID to delete: ");
        if (gradeService.deleteGrade(gradeId)) {
            System.out.println("Grade deleted successfully!");
        }
    }
    
    private static void reportMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Generate Student Report");
            System.out.println("2. Generate Summary Report");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        int studentId = getIntInput("Enter student ID: ");
                        reportService.generateStudentReport(studentId);
                        System.out.println("Report generated successfully! Check the reports/ directory.");
                        break;
                    case 2:
                        reportService.generateSummaryReport();
                        System.out.println("Summary report generated successfully! Check the reports/ directory.");
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void demonstrateCollections() {
        System.out.println("\n--- Collections Framework Demonstration ---");
        
        try {
            List<Student> students = studentService.getAllStudents();
            
            // ArrayList operations
            ArrayList<Student> studentList = new ArrayList<>(students);
            System.out.println("ArrayList size: " + studentList.size());
            
            // Demonstrating Collections operations
            if (!studentList.isEmpty()) {
                System.out.println("First student: " + studentList.get(0).getName());
                System.out.println("Last student: " + studentList.get(studentList.size() - 1).getName());
            }
            
            // Using Stream API with Collections
            long count = studentList.stream()
                    .filter(s -> s.getDepartment() != null)
                    .count();
            System.out.println("Students with department: " + count);
            
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateMultithreading() {
        System.out.println("\n--- Multithreading Demonstration ---");
        
        try {
            List<Student> students = studentService.getAllStudents();
            
            if (students.isEmpty()) {
                System.out.println("No students available for processing.");
                return;
            }
            
            System.out.println("Processing " + students.size() + " students using multithreading...");
            TaskManager taskManager = new TaskManager(3); // 3 threads
            taskManager.processStudentsConcurrently(students);
            
            // Wait a bit for tasks to complete
            Thread.sleep(2000);
            
            taskManager.shutdown();
            System.out.println("Multithreading demonstration completed. Check logs for details.");
            
        } catch (CustomException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateFileOperations() {
        System.out.println("\n--- File Operations Demonstration ---");
        
        try {
            List<Student> students = studentService.getAllStudents();
            
            // This will be demonstrated through report generation
            System.out.println("File operations are demonstrated through:");
            System.out.println("1. Logging (check logs/application.log)");
            System.out.println("2. Report generation (check reports/ directory)");
            System.out.println("\nGenerating a sample report...");
            
            if (!students.isEmpty()) {
                reportService.generateStudentReport(students.get(0).getStudentId());
                System.out.println("Sample report generated. Check reports/ directory.");
            } else {
                System.out.println("No students available for report generation.");
            }
            
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void demonstratePolymorphism() {
        System.out.println("\n--- Polymorphism Demonstration ---");
        
        // Creating Instructor objects (inheritance from Person)
        Instructor instructor1 = new Instructor("Dr. Smith", "smith@vit.ac.in", "Computer Science", "EMP001");
        Instructor instructor2 = new Instructor("Dr. Jones", "jones@vit.ac.in", "Mathematics", "EMP002");
        
        // Demonstrating polymorphism - using Person reference
        List<com.vitproject.model.Person> people = new ArrayList<>();
        people.add(instructor1);
        people.add(instructor2);
        
        System.out.println("Polymorphism example - Person references:");
        for (com.vitproject.model.Person person : people) {
            // Method overriding - each subclass implements getDisplayInfo differently
            System.out.println(person.getDisplayInfo());
        }
    }
    
    // Utility methods for input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}

