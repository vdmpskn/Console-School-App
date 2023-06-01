package com.foxminded.vdmpskn.schoolconsoleapp.option;

import com.foxminded.vdmpskn.schoolconsoleapp.manage.StudentManager;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteStudentOption implements MenuOption {
    private final StudentManager studentManager;
    private final Scanner scanner;

    public DeleteStudentOption(StudentManager studentManager, Scanner scanner) {
        this.studentManager = studentManager;
        this.scanner = scanner;
    }

    public void execute() throws SQLException {
        System.out.println("=== Delete Student ===");
        System.out.print("Enter students' ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        try{
            studentManager.deleteStudent(studentId);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}

