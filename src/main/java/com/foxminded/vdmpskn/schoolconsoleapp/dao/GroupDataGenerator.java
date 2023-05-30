package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.logging.Log;

public class GroupDataGenerator {
    private static final int NUM_GROUPS = 10;
    private static final Log log = LogFactory.getLog(GroupDataGenerator.class);
    static DatabaseConnector connector = new DatabaseConnector();


    public static void generateAndInsertGroups() throws SQLException {
        try {
            generateRandom();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateRandom() throws SQLException {
        List<String> groupNames = generateRandomGroupNames(NUM_GROUPS);
        Connection connection = connector.getConnection();

        String sql = "INSERT INTO groups (group_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String groupName : groupNames) {
                statement.setString(1, groupName);
                statement.addBatch();
            }
            statement.executeBatch();
            log.info("Group Data inserted succeflully");
        }
    }

    public static List<String> generateRandomGroupNames(int numGroups) {
        List<String> groupNames = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numGroups; i++) {
            String groupName = generateRandomGroupName(random);
            groupNames.add(groupName);
        }
        return groupNames;
    }

    private static String generateRandomGroupName(Random random) {
        return String.valueOf(generateRandomCharacter(random)) +
                generateRandomCharacter(random) +
                "-" +
                generateRandomNumber(random) +
                generateRandomNumber(random);
    }

    private static int generateRandomNumber(Random random) {
        int numberStart = 0;
        int numberEnd = 9;
        return random.nextInt(numberEnd - numberStart + 1) + numberStart;
    }

    private static char generateRandomCharacter(Random random) {
        int asciiStart = 65; // ASCII code for 'A'
        int asciiEnd = 90; // ASCII code for 'Z'
        int randomAscii = random.nextInt(asciiEnd - asciiStart + 1) + asciiStart;
        return (char) randomAscii;
    }


}
