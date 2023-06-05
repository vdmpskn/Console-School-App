package com.foxminded.vdmpskn.spring.schoolconsoleapp.front;

import com.foxminded.vdmpskn.spring.schoolconsoleapp.dao.CoursesDataGenerator;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.dao.DatabaseConnector;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.logic.CourseEnrollmentAnalyzer;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.option.*;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.logic.GroupStudentCountAnalyzer;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.manage.CourseManager;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.manage.StudentManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ConsoleMenu {
    private final DatabaseConnector connector;
    private final Map<Integer, MenuOption> menuOptions;

    public ConsoleMenu(DatabaseConnector connector) {
        this.connector = connector;
        this.menuOptions = createMenuOptions();
    }

    private Map<Integer, MenuOption> createMenuOptions() {
        Map<Integer, MenuOption> options = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        GroupStudentCountAnalyzer groupStudentCountAnalyzer = new GroupStudentCountAnalyzer(connector);
        CourseEnrollmentAnalyzer courseEnrollmentAnalyzer = new CourseEnrollmentAnalyzer(connector);
        StudentManager studentManager = new StudentManager(connector);
        CourseManager courseManager = new CourseManager(connector);
        CoursesDataGenerator coursesDataGenerator = new CoursesDataGenerator(connector);

        options.put(1, new FindGroupsWithMaxStudentsOption(groupStudentCountAnalyzer, scanner));
        options.put(2, new FindStudentsByCourseNameOption(courseEnrollmentAnalyzer, scanner));
        options.put(3, new AddNewStudentOption(studentManager, scanner));
        options.put(4, new DeleteStudentOption(studentManager, scanner));
        options.put(5, new AddStudentToCourseOption(courseManager, studentManager, coursesDataGenerator, scanner));
        options.put(6, new RemoveStudentFromCourseOption(courseManager, scanner));

        return options;
    }

    public void getConsole() {
        try (Connection connection = connector.getConnection()) {
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

                if (menuOptions.containsKey(choice)) {
                    MenuOption menuOption = menuOptions.get(choice);
                    menuOption.execute();
                } else if (choice == 7) {
                    running = false;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("Exiting the program");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
