package com.foxminded.vdmpskn.schoolconsoleapp.option;

import com.foxminded.vdmpskn.schoolconsoleapp.front.MenuOption;
import com.foxminded.vdmpskn.schoolconsoleapp.logic.GroupStudentCountAnalyzer;

import java.sql.SQLException;
import java.util.Scanner;

public class FindGroupsWithMaxStudentsOption implements MenuOption {
    private final GroupStudentCountAnalyzer groupStudentCountAnalyzer;
    private final Scanner scanner;

    public FindGroupsWithMaxStudentsOption(GroupStudentCountAnalyzer groupStudentCountAnalyzer, Scanner scanner) {
        this.groupStudentCountAnalyzer = groupStudentCountAnalyzer;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter maximum number of students: ");
        int maxStudents = scanner.nextInt();
        scanner.nextLine();

        try {
            groupStudentCountAnalyzer.findGroupsWithMaxStudents(maxStudents);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
