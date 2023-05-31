package com.foxminded.vdmpskn.schoolconsoleapp.logic;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;
import com.foxminded.vdmpskn.schoolconsoleapp.logic.CourseEnrollmentAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CourseEnrollmentAnalyzerTest {

    @Mock
    private DatabaseConnector mockConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private CourseEnrollmentAnalyzer analyzer;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("first_name")).thenReturn("John");
        when(mockResultSet.getString("last_name")).thenReturn("Doe");

        analyzer = new CourseEnrollmentAnalyzer(mockConnector);
    }

    @Test
    void findStudentsByCourseName_ShouldReturnListOfStudents_WhenCourseNameExists() throws SQLException {
        String courseName = "Math";
        List<String> expectedStudents = new ArrayList<>();
        expectedStudents.add("John Doe");
        expectedStudents.add("John Doe");

        doNothing().when(mockStatement).setString(1, courseName);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        List<String> actualStudents = analyzer.findStudentsByCourseName(courseName);

        assertEquals(expectedStudents, actualStudents);
        verify(mockStatement, times(1)).setString(1, courseName);
        actualStudents.clear();
    }

}



