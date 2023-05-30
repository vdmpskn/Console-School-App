package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GroupDataGenerator {
    private static final int NUM_GROUPS = 10;
    private static final Log log = LogFactory.getLog(GroupDataGenerator.class);

    public static void generateAndInsertGroups(Connection connection) throws SQLException{

    try{
        generateRandom(connection);
    } catch (SQLException e){
        e.printStackTrace();
    }

}

public static void generateRandom(Connection connection) throws SQLException{
    List<String> groupNames = generateRandomGroupNames(NUM_GROUPS);

    String sql = "INSERT INTO groups (group_name) VALUES (?)";
    try(PreparedStatement statement = connection.prepareStatement(sql)){
        for(String groupName : groupNames){
            statement.setString(1,groupName);
            statement.addBatch();
        }
        statement.executeBatch();
        log.info("Group Data inserted succeflully");
    }
}

public static List<String> generateRandomGroupNames(int numGroups){
    List<String> groupNames = new ArrayList<>();
    Random random = new Random();
    for (int i =0; i<numGroups; i++){
        String groupName = generateRandomGroupName(random);
        groupNames.add(groupName);
    }
    return groupNames;
}
    private static String generateRandomGroupName(Random random) {
        StringBuilder groupName = new StringBuilder();
        groupName.append(generateRandomCharacter(random));
        groupName.append(generateRandomCharacter(random));
        groupName.append("-");
        groupName.append(generateRandomNumber(random));
        groupName.append(generateRandomNumber(random));

        return groupName.toString();
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
