package com.foxminded.vdmpskn.schoolconsoleapp.front;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.CoursesDataGenerator;
import com.foxminded.vdmpskn.schoolconsoleapp.manage.CourseManager;
import com.foxminded.vdmpskn.schoolconsoleapp.manage.StudentManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.foxminded.vdmpskn.schoolconsoleapp.logic.GroupStudentCountAnalyzer.findGroupsWithMaxStudents;
import static com.foxminded.vdmpskn.schoolconsoleapp.logic.CourseEnrollmentAnalyzer.findStudentsByCourseName;

public class ConsoleMenu {
    private static List<String> courseList;

    public ConsoleMenu(List<String> courseList) {
        this.courseList = courseList;
    }
    public static void getConsole(Connection connection){

        try {

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("=== Console Menu ===");
                System.out.println("1. Find all groups with less or equal students' number");
                System.out.println("2. Find all students related to a course");
                System.out.println("3. Add new student");
                System.out.println("4. Delete Student");
                System.out.println("5. Add Student to Course");
                System.out.println("6. Remove Student from Course");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter maximum number of students: ");
                        int maxStudents = scanner.nextInt();
                        scanner.nextLine();

                        findGroupsWithMaxStudents(connection, maxStudents);
                        break;
                    case 2:
                        System.out.print("Enter course name: ");
                        String courseName = scanner.nextLine();

                        List<String> students = findStudentsByCourseName(courseName, connection);
                        if (!students.isEmpty()) {
                            System.out.println("Students related to the course: " + courseName);
                            for (String student : students) {
                                System.out.println(student);
                            }
                        } else {
                            System.out.println("No students found for the course: " + courseName);
                        }
                        break;
                    case 3:
                        System.out.println("=== Add a New Student ===");
                        System.out.print("Enter first name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter last name: ");
                        String lastName = scanner.nextLine();
                        StudentManager.addNewStudent(connection, firstName, lastName);
                        break;
                    case 4:
                        System.out.println("=== Delete Student ===");
                        System.out.print("Enter students' ID: ");
                        int studentId  = scanner.nextInt();
                        StudentManager.deleteStudent(connection, studentId);
                        break;
                    case 5:
                        System.out.println("=== Add Student to Course ===");
                        System.out.print("Enter student ID: ");
                        studentId = scanner.nextInt();
                        scanner.nextLine();

                        List<String> courseNames = CoursesDataGenerator.getAllCourseNames(connection);

                        if (courseNames != null && !courseNames.isEmpty()) {
                            for (int i = 0; i < courseNames.size(); i++) {
                                System.out.println((i + 1) + ". " + courseNames.get(i));
                            }

                            System.out.print("Enter course ID to add the student: ");
                            int courseId = scanner.nextInt();
                            scanner.nextLine();
                            CourseManager.addStudentToCourse(connection, studentId, courseId);
                        } else {
                            System.out.println("No available courses found.");
                        }
                        break;
                    case 6:
                        System.out.println("=== Remove Student from Course ===");
                        System.out.print("Enter student ID: ");
                        studentId = scanner.nextInt();
                        scanner.nextLine();

                        List<String> studentCourses = CourseManager.getStudentCourses(connection, studentId);

                        if (studentCourses != null && !studentCourses.isEmpty()) {
                            System.out.println("Student's courses:");
                            for (String course : studentCourses) {
                                System.out.println(course);
                            }
                            System.out.print("Enter course name to remove the student from: ");
                            courseName = scanner.nextLine();
                            CourseManager.removeStudentFromCourse(connection, studentId, courseName);
                        } else {
                            System.out.println("No courses found for the student.");
                        }
                        break;
                    case 7:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("Exiting the program");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

