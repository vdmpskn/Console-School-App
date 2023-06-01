package com.foxminded.vdmpskn.schoolconsoleapp.option;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.CoursesDataGenerator;
import com.foxminded.vdmpskn.schoolconsoleapp.front.MenuOption;
import com.foxminded.vdmpskn.schoolconsoleapp.manage.CourseManager;
import com.foxminded.vdmpskn.schoolconsoleapp.manage.StudentManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddStudentToCourseOption implements MenuOption {
    private final CourseManager courseManager;
    private final StudentManager studentManager;
    private final CoursesDataGenerator coursesDataGenerator;
    private final Scanner scanner;

    public AddStudentToCourseOption(CourseManager courseManager, StudentManager studentManager,
                                    CoursesDataGenerator coursesDataGenerator, Scanner scanner) {
        this.courseManager = courseManager;
        this.studentManager = studentManager;
        this.coursesDataGenerator = coursesDataGenerator;
        this.scanner = scanner;
    }

    public void execute() throws SQLException {
        System.out.println("=== Add Student to Course ===");
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        try{
            List<String> courseNames = coursesDataGenerator.getAllCourseNames();
            if (courseNames != null && !courseNames.isEmpty()) {
                for (int i = 0; i < courseNames.size(); i++) {
                    System.out.println((i + 1) + ". " + courseNames.get(i));
                }

                System.out.print("Enter course ID to add the student: ");
                int courseId = scanner.nextInt();
                scanner.nextLine();
                courseManager.addStudentToCourse(studentId, courseId);
            } else {
                System.out.println("No available courses found.");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }



    }
}

