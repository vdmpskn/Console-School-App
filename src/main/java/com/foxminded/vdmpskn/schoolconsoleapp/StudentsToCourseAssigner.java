package com.foxminded.vdmpskn.schoolconsoleapp;

import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StudentsToCourseAssigner {

    public static void assignCoursesToStudents(String DB_URL, String USER, String PASSWORD) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Random random = new Random();

            String selectStudentsSQL = "SELECT student_id FROM students";
            Statement selectStatement = connection.createStatement();
            ResultSet studentIdsResult = selectStatement.executeQuery(selectStudentsSQL);

            while (studentIdsResult.next()) {
                int studentId = studentIdsResult.getInt("student_id");

                int numCourses = random.nextInt(3) + 1;

                String selectCoursesSQL = "SELECT course_id FROM courses ORDER BY RANDOM() LIMIT " + numCourses;
                Statement selectCoursesStatement = connection.createStatement();
                ResultSet courseIdsResult = selectCoursesStatement.executeQuery(selectCoursesSQL);

                Set<Integer> existingCourseIds = new HashSet<>();
                String selectExistingCoursesSQL = "SELECT course_id FROM student_courses WHERE student_id = " + studentId;
                Statement selectExistingCoursesStatement = connection.createStatement();
                ResultSet existingCourseIdsResult = selectExistingCoursesStatement.executeQuery(selectExistingCoursesSQL);
                while (existingCourseIdsResult.next()) {
                    existingCourseIds.add(existingCourseIdsResult.getInt("course_id"));
                }

                while (courseIdsResult.next()) {
                    int courseId = courseIdsResult.getInt("course_id");

                    if (!existingCourseIds.contains(courseId)) {
                        String insertCourseSQL = "INSERT INTO student_courses (student_id, course_id) VALUES (" + studentId + ", " + courseId + ")";
                        Statement insertStatement = connection.createStatement();
                        insertStatement.executeUpdate(insertCourseSQL);

                        existingCourseIds.add(courseId);
                    }
                }
            }

            System.out.println("Courses assigned to students successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

