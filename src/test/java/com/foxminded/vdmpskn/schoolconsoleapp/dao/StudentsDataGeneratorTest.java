package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentsDataGeneratorTest {

    @Test
    public void testGetRandomGroupId() {
        StudentsDataGenerator generator = new StudentsDataGenerator();
        Random random = new Random();
        List<Integer> groupIds = Arrays.asList(1, 2, 3, 4, 5);

        int randomGroupId = generator.getRandomGroupId(groupIds, random);

        assertTrue(groupIds.contains(randomGroupId), "Random group ID is not in the list of group IDs");
    }

    @Test
    public void testGetRandomGroupIdEmptyList() {
        StudentsDataGenerator generator = new StudentsDataGenerator();
        Random random = new Random();
        List<Integer> groupIds = new ArrayList<>();

        assertThrows(IllegalStateException.class, () -> generator.getRandomGroupId(groupIds, random),
                "Empty group IDs list should throw an IllegalStateException");
    }



}
