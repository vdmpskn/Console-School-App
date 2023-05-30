package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StudentsDataGenerator {
    private static final Log log = LogFactory.getLog(StudentsDataGenerator.class);
    static DatabaseConnector connector = new DatabaseConnector();

    private static final String[] FIRST_NAMES = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Oliver", "Isabella",
            "Benjamin", "Mia", "Lucas", "Charlotte", "Henry", "Amelia", "Alexander", "Harper", "Daniel", "Evelyn"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Clark"};

    public static void generateStudents() throws SQLException {

        String sql = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
        Random random = new Random();
        Connection connection = connector.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Integer> groupIds = getGroupIds();

            if (groupIds.isEmpty()) {
                log.fatal("No group IDs available. Insert group records into the 'groups' table.");
                throw new IllegalStateException();
            }

            for (int i = 0; i < 200; i++) {
                int groupId = getRandomGroupId(groupIds, random);
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

                statement.setInt(1, groupId);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.addBatch();
            }

            statement.executeBatch();
            log.info("Students generated successfully.");
        }
    }

    private static List<Integer> getGroupIds() throws SQLException {
        List<Integer> groupIds = new ArrayList<>();
        Connection connection = connector.getConnection();

        String sql = "SELECT group_id FROM groups";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                groupIds.add(groupId);
            }
        }

        return groupIds;
    }

    private static int getRandomGroupId(List<Integer> groupIds, Random random) {
        if (groupIds.isEmpty()) {
            log.fatal("No group IDs available");
            throw new IllegalStateException();
        }
        int randomIndex = random.nextInt(groupIds.size());
        return groupIds.get(randomIndex);
    }
}



