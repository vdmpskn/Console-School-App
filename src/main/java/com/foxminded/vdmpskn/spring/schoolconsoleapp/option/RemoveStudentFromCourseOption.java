package com.foxminded.vdmpskn.spring.schoolconsoleapp.option;

import com.foxminded.vdmpskn.spring.schoolconsoleapp.manage.CourseManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RemoveStudentFromCourseOption implements MenuOption {
    private final CourseManager courseManager;
    private final Scanner scanner;

    public RemoveStudentFromCourseOption(CourseManager courseManager, Scanner scanner) {
        this.courseManager = courseManager;
        this.scanner = scanner;
    }

    public void execute() throws SQLException {
        System.out.println("=== Remove Student from Course ===");
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        try{
            List<String> studentCourses = courseManager.getStudentCourses(studentId);

            if (studentCourses != null && !studentCourses.isEmpty()) {
                System.out.println("Student's courses:");
                for (String course : studentCourses) {
                    System.out.println(course);
                }
                System.out.print("Enter course name to remove the student from: ");
                String courseName = scanner.nextLine();
                courseManager.removeStudentFromCourse(studentId, courseName);
            } else {
                System.out.println("No courses found for the student.");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}

