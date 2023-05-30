package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GroupDataGeneratorTest {

    @Test
    public void testGenerateRandomGroupNames() {
        int numGroups = 10;
        List<String> groupNames = GroupDataGenerator.generateRandomGroupNames(numGroups);

        assertEquals(numGroups, groupNames.size(), "Incorrect number of group names generated");

        for (String groupName : groupNames) {
            assertTrue(groupName.matches("[A-Z]{2}-\\d{2}"), "Invalid group name format");
        }
    }

    @Test
    public void testGenerateRandomGroupName() {
        GroupDataGenerator generator = new GroupDataGenerator();
        Random random = new Random();
        String groupName = generator.generateRandomGroupName(random);

        assertTrue(groupName.matches("[A-Z]{2}-\\d{2}"), "Invalid group name format");
    }

    @Test
    public void testGenerateRandomNumber() {
        GroupDataGenerator generator = new GroupDataGenerator();
        Random random = new Random();
        int randomNumber = generator.generateRandomNumber(random);

        assertTrue(randomNumber >= 0 && randomNumber <= 9, "Random number is out of range");
    }

    @Test
    public void testGenerateRandomCharacter() {
        GroupDataGenerator generator = new GroupDataGenerator();
        Random random = new Random();
        char randomCharacter = generator.generateRandomCharacter(random);

        assertTrue(Character.isUpperCase(randomCharacter), "Random character is not uppercase");
        assertTrue(randomCharacter >= 'A' && randomCharacter <= 'Z', "Random character is out of range");
    }



    @Test
    public void testGenerateAndInsertGroups() throws SQLException {
        assertDoesNotThrow(() -> GroupDataGenerator.generateAndInsertGroups());
    }
}
