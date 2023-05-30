package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CoursesDataGenerator {
    private static final Log log = LogFactory.getLog(CoursesDataGenerator.class);
    public static void createCourse(Connection connection) throws SQLException{
        try {
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
            log.info("Courses created successfully.");
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
