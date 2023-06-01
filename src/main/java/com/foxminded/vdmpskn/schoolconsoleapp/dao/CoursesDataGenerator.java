package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CoursesDataGenerator {

    private final DatabaseConnector connector;

    public CoursesDataGenerator(DatabaseConnector connector) {
        this.connector = connector;
    }

    private static final Log log = LogFactory.getLog(CoursesDataGenerator.class);

    public void createCourse() throws SQLException {
        try {
            addCourses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCourses() throws SQLException {
        String sql = "INSERT INTO courses (course_name, course_description) SELECT ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM courses WHERE course_name = ?)";


        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Math");
            statement.setString(2, "Mathematics course");
            statement.setString(3, "Math");
            statement.addBatch();

            statement.setString(1, "Biology");
            statement.setString(2, "Biology course");
            statement.setString(3, "Biology");
            statement.addBatch();

            statement.setString(1, "History");
            statement.setString(2, "History course");
            statement.setString(3, "History");
            statement.addBatch();

            statement.setString(1, "Chemistry");
            statement.setString(2, "Chemistry course");
            statement.setString(3, "Chemistry");
            statement.addBatch();

            statement.setString(1, "Physics");
            statement.setString(2, "Physics course");
            statement.setString(3, "Physics");
            statement.addBatch();

            statement.setString(1, "English");
            statement.setString(2, "English course");
            statement.setString(3, "English");
            statement.addBatch();

            statement.setString(1, "Computer Science");
            statement.setString(2, "Computer Science course");
            statement.setString(3, "Computer Science");
            statement.addBatch();

            statement.setString(1, "Art");
            statement.setString(2, "Art course");
            statement.setString(3, "Art");
            statement.addBatch();

            statement.setString(1, "Music");
            statement.setString(2, "Music course");
            statement.setString(3, "Music");
            statement.addBatch();

            statement.setString(1, "Physical Education");
            statement.setString(2, "Physical Education course");
            statement.setString(3, "Physical Education");
            statement.addBatch();

            statement.executeBatch();
            log.info("Courses created successfully.");
        } catch (SQLException e){
            log.error("Failed to add courses: " + e.getMessage());
        }
    }

    public List<String> getAllCourseNames() throws SQLException {
        List<String> courseNames = new ArrayList<>();

        String sql = "SELECT course_name FROM courses";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                courseNames.add(courseName);
            }
        } catch (SQLException e) {
            log.error("Failed to retrieve course names: " + e.getMessage());
        }

        System.out.println("Retrieved course names: ");
        return courseNames;
    }

}
