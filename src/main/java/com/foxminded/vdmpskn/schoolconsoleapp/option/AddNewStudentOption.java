package com.foxminded.vdmpskn.schoolconsoleapp.option;

import com.foxminded.vdmpskn.schoolconsoleapp.front.MenuOption;
import com.foxminded.vdmpskn.schoolconsoleapp.manage.StudentManager;

import java.sql.SQLException;
import java.util.Scanner;

public class AddNewStudentOption implements MenuOption {
    private final StudentManager studentManager;
    private final Scanner scanner;

    public AddNewStudentOption(StudentManager studentManager, Scanner scanner) {
        this.studentManager = studentManager;
        this.scanner = scanner;
    }

    public void execute() throws SQLException {
        System.out.println("=== Add a New Student ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        try {
            studentManager.addNewStudent(firstName, lastName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
