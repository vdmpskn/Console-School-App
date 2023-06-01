package com.foxminded.vdmpskn.schoolconsoleapp.option;

import com.foxminded.vdmpskn.schoolconsoleapp.front.MenuOption;
import com.foxminded.vdmpskn.schoolconsoleapp.logic.CourseEnrollmentAnalyzer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FindStudentsByCourseNameOption implements MenuOption {
    private final CourseEnrollmentAnalyzer courseEnrollmentAnalyzer;
    private final Scanner scanner;

    public FindStudentsByCourseNameOption(CourseEnrollmentAnalyzer courseEnrollmentAnalyzer, Scanner scanner) {
        this.courseEnrollmentAnalyzer = courseEnrollmentAnalyzer;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
            List<String> students = courseEnrollmentAnalyzer.findStudentsByCourseName(courseName);
            if (!students.isEmpty()) {
                System.out.println("Students related to the course: " + courseName);
                for (String student : students) {
                    System.out.println(student);
                }
            } else {
                System.out.println("No students found for the course: " + courseName);
            }
    }
}
