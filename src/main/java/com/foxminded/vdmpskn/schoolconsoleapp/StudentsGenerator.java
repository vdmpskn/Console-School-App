package com.foxminded.vdmpskn.schoolconsoleapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class StudentsGenerator {
    private static final String[] FIRST_NAMES = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Oliver", "Isabella",
            "Benjamin", "Mia", "Lucas", "Charlotte", "Henry", "Amelia", "Alexander", "Harper", "Daniel", "Evelyn"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Clark"};

    public static void generateStudents(String DB_URL,String USER, String PASSWORD) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            System.out.println("Connected to the database");

            addStudents(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudents(Connection connection) throws SQLException {
        String sql = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
        Random random = new Random();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < 200; i++) {
                int groupId = random.nextInt(10) + 1; // Generate group ID from 1 to 10
                String firstName = FIRST_NAMES[random.nextInt(20)];
                String lastName = LAST_NAMES[random.nextInt(20)];

                statement.setInt(1, groupId);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.addBatch();
            }

            statement.executeBatch();
            System.out.println("Students generated successfully.");
        }
    }

}
