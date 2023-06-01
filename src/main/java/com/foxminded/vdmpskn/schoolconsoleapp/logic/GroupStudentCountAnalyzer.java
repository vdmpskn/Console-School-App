package com.foxminded.vdmpskn.schoolconsoleapp.logic;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupStudentCountAnalyzer {

    private final DatabaseConnector connector;

    public GroupStudentCountAnalyzer(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void findGroupsWithMaxStudents(int maxStudents) throws SQLException {
        String sql = "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) AS student_count "
                + "FROM groups "
                + "LEFT JOIN students ON groups.group_id = students.group_id "
                + "GROUP BY groups.group_id, groups.group_name "
                + "HAVING COUNT(students.student_id) <= ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, maxStudents);

            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Groups with less or equal students' number:");
                while (resultSet.next()) {
                    int groupId = resultSet.getInt("group_id");
                    String groupName = resultSet.getString("group_name");
                    int studentCount = resultSet.getInt("student_count");

                    System.out.println("Group ID: " + groupId);
                    System.out.println("Group Name: " + groupName);
                    System.out.println("Student Count: " + studentCount);
                    System.out.println();
                }
            }
        }
    }

}
