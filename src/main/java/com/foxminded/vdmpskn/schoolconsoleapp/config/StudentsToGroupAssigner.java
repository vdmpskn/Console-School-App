package com.foxminded.vdmpskn.schoolconsoleapp.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StudentsToGroupAssigner {
    private static final Log log = LogFactory.getLog(StudentsToGroupAssigner.class);

    public static void assignStudentsToGroups() {
        DatabaseConnector connector = new DatabaseConnector();

        try {
            Connection connection = connector.getConnection();
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

            log.info("Students assigned to groups successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
