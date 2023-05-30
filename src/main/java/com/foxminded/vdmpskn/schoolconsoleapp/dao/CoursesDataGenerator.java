package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDataGenerator {

    public static void createCourse(Connection connection) throws SQLException{
        try {
            System.out.println("Connected to the database");

            addCourses(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCourses(Connection connection) throws SQLException {
        String sql = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Math");
            statement.setString(2, "Mathematics course");
            statement.addBatch();

            statement.setString(1, "Biology");
            statement.setString(2, "Biology course");
            statement.addBatch();

            statement.setString(1, "History");
            statement.setString(2, "History course");
            statement.addBatch();

            statement.setString(1, "Chemistry");
            statement.setString(2, "Chemistry course");
            statement.addBatch();

            statement.setString(1, "Physics");
            statement.setString(2, "Physics course");
            statement.addBatch();

            statement.setString(1, "English");
            statement.setString(2, "English course");
            statement.addBatch();

            statement.setString(1, "Computer Science");
            statement.setString(2, "Computer Science course");
            statement.addBatch();

            statement.setString(1, "Art");
            statement.setString(2, "Art course");
            statement.addBatch();

            statement.setString(1, "Music");
            statement.setString(2, "Music course");
            statement.addBatch();

            statement.setString(1, "Physical Education");
            statement.setString(2, "Physical Education course");
            statement.addBatch();

            statement.executeBatch();
            System.out.println("Courses created successfully.");
        }
    }
    public static List<String> getAllCourseNames(Connection connection) throws SQLException {
        List<String> courseNames = new ArrayList<>();

        String sql = "SELECT course_name FROM courses";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                courseNames.add(courseName);
            }
        }

        return courseNames;
    }

}
