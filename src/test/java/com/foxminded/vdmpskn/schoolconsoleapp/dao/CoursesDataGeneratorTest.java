package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CoursesDataGeneratorTest {

    @Mock
    private DatabaseConnector mockConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private CoursesDataGenerator dataGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        dataGenerator = new CoursesDataGenerator(mockConnector);
    }

    @Test
    void getAllCourseNames_ShouldRetrieveCourseNamesSuccessfully() throws SQLException {
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("course_name")).thenReturn("Math", "Physics");

        List<String> courseNames = dataGenerator.getAllCourseNames();

        verify(mockConnector).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockStatement).close();
        verify(mockConnection).close();
        verify(mockResultSet).close();
        assertEquals(Arrays.asList("Math", "Physics"), courseNames);
    }

    @Test
    void getAllCourseNames_ShouldLogError_WhenSQLExceptionOccurs() throws SQLException {
        when(mockStatement.executeQuery()).thenThrow(new SQLException("Query error"));

        List<String> courseNames = dataGenerator.getAllCourseNames();

        verify(mockConnector).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement).executeQuery();
        verify(mockStatement).close();
        verify(mockConnection).close();
        verify(mockResultSet, never()).close();
        verify(mockResultSet, never()).getString("course_name");
        assertEquals(new ArrayList<>(), courseNames);
    }

}
