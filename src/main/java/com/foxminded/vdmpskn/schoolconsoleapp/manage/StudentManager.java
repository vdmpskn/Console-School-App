package com.foxminded.vdmpskn.schoolconsoleapp.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class StudentManager {

        public static void addNewStudent(Connection connection, String firstName, String lastName) throws SQLException {
            String insertStudentQuery = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertStudentQuery)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.executeUpdate();
                System.out.println("New student added successfully.");
            }
        }

        public static void deleteStudent(Connection connection, int studentId) throws SQLException {
            try {

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

