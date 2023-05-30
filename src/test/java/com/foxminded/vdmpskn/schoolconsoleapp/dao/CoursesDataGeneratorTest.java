package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoursesDataGeneratorTest {

    @Test
    public void testCreateCourse() {
        assertDoesNotThrow(() -> CoursesDataGenerator.createCourse());
    }

    @Test
    public void testGetAllCourseNames() throws SQLException {
        List<String> courseNames = CoursesDataGenerator.getAllCourseNames();

        assertNotNull(courseNames);
        assertFalse(courseNames.isEmpty());
        assertEquals(10, courseNames.size());
        assertTrue(courseNames.contains("Math"));
        assertTrue(courseNames.contains("Biology"));
        assertTrue(courseNames.contains("History"));
        assertTrue(courseNames.contains("Chemistry"));
        assertTrue(courseNames.contains("Physics"));
        assertTrue(courseNames.contains("English"));
        assertTrue(courseNames.contains("Computer Science"));
        assertTrue(courseNames.contains("Art"));
        assertTrue(courseNames.contains("Music"));
        assertTrue(courseNames.contains("Physical Education"));
    }
}
