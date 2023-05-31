package com.foxminded.vdmpskn.schoolconsoleapp.logic;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseEnrollmentAnalyzer {

    public List<String> findStudentsByCourseName(String courseName) {
        List<String> students = new ArrayList<>();
        DatabaseConnector connector = new DatabaseConnector();

        String query = "SELECT s.first_name, s.last_name " +
                "FROM students s " +
                "INNER JOIN student_courses sc ON s.student_id = sc.student_id " +
                "INNER JOIN courses c ON sc.course_id = c.course_id " +
                "WHERE c.course_name = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, courseName);
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                students.add(firstName + " " + lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

}
