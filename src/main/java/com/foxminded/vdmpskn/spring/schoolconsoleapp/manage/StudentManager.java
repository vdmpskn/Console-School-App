package com.foxminded.vdmpskn.spring.schoolconsoleapp.manage;

import com.foxminded.vdmpskn.spring.schoolconsoleapp.dao.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentManager {

    private final DatabaseConnector connector;

    public StudentManager(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void addNewStudent(String firstName, String lastName) throws SQLException {
        String insertStudentQuery = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertStudentQuery)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.executeUpdate();
            System.out.println("New student added successfully.");
        }
    }

    public void deleteStudent( int studentId) throws SQLException {
        try(Connection connection = connector.getConnection();) {

            String deleteStudentCoursesQuery = "DELETE FROM student_courses WHERE student_id = ?";
            PreparedStatement deleteStudentCoursesStatement = connection.prepareStatement(deleteStudentCoursesQuery);
            deleteStudentCoursesStatement.setInt(1, studentId);
            deleteStudentCoursesStatement.executeUpdate();

            String deleteStudentQuery = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement deleteStudentStatement = connection.prepareStatement(deleteStudentQuery);
            deleteStudentStatement.setInt(1, studentId);
            deleteStudentStatement.executeUpdate();

            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

