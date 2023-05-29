package com.foxminded.vdmpskn.schoolconsoleapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static com.foxminded.vdmpskn.schoolconsoleapp.GroupMaxStudentFinder.findGroupsWithMaxStudents;

public class ConsoleMenu {
    public static void getConsole(String DB_URL, String USER, String PASSWORD){

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            System.out.println("Connected to the database");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("=== Console Menu ===");
                System.out.println("1. Find all groups with less or equal students' number");
                System.out.println("2. Exit");
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

