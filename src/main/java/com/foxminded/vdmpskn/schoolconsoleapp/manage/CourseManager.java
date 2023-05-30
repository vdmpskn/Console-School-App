package com.foxminded.vdmpskn.schoolconsoleapp.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    public static void addStudentToCourse(Connection connection, int studentId, int courseId) throws SQLException {
        String addStudentToCourseQuery = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addStudentToCourseQuery)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added to the course successfully.");
            } else {
                System.out.println("Failed to add student to the course.");
            }
        }
    }

    public static void printAvailableCourses(List<String> courses) {
        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
    }
    public static List<String> getStudentCourses(Connection connection, int studentId) throws SQLException {
        List<String> courseNames = new ArrayList<>();

        String sql = "SELECT c.course_name " +
                "FROM courses c " +
                "JOIN student_courses sc ON c.course_id = sc.course_id " +
                "WHERE sc.student_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    courseNames.add(courseName);
                }
            }
        }

        return courseNames;
    }

    public static void removeStudentFromCourse(Connection connection, int studentId, String courseName) throws SQLException {
        String sql = "DELETE FROM student_courses " +
                "WHERE student_id = ? AND course_id IN (SELECT course_id FROM courses WHERE course_name = ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setString(2, courseName);
            statement.executeUpdate();
            System.out.println("Student removed from the course successfully.");
        }
    }
}

