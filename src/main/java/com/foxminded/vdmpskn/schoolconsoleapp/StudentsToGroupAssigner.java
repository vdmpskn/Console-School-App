package com.foxminded.vdmpskn.schoolconsoleapp;

import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class StudentsToGroupAssigner {

    public static void assignStudentsToGroups(String DB_URL, String USER, String PASSWORD) throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String selectStudentsQuery = "SELECT student_id FROM students";
            PreparedStatement selectStudentsStatement = connection.prepareStatement(selectStudentsQuery);
            Random random = new Random();

            String selectGroupsQuery = "SELECT group_id FROM groups";
            PreparedStatement selectGroupsStatement = connection.prepareStatement(selectGroupsQuery);

            List<Integer> studentIds = new ArrayList<>();
            List<Integer> groupIds = new ArrayList<>();

            try (ResultSet studentsResultSet = selectStudentsStatement.executeQuery();
                 ResultSet groupsResultSet = selectGroupsStatement.executeQuery()) {

                while (studentsResultSet.next()) {
                    int studentId = studentsResultSet.getInt("student_id");
                    studentIds.add(studentId);
                }

                while (groupsResultSet.next()) {
                    int groupId = groupsResultSet.getInt("group_id");
                    groupIds.add(groupId);
                }
            }

            String updateStudentsQuery = "UPDATE students SET group_id = ? WHERE student_id = ?";
            PreparedStatement updateStudentsStatement = connection.prepareStatement(updateStudentsQuery);

            for (int studentId : studentIds) {
                if (groupIds.isEmpty()) {
                    break;
                }

                int randomIndex = random.nextInt(groupIds.size());
                int groupId = groupIds.get(randomIndex);
                groupIds.remove(randomIndex);

                updateStudentsStatement.setInt(1, groupId);
                updateStudentsStatement.setInt(2, studentId);
                updateStudentsStatement.executeUpdate();
            }

            System.out.println("Students assigned to groups successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
