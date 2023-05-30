package com.foxminded.vdmpskn.schoolconsoleapp.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CourseEnrollmentAnalyzerTest {

    @Test
    public void testFindStudentsByCourseNameWithSQLException() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("Mock SQL exception"));

        List<String> students = CourseEnrollmentAnalyzer.findStudentsByCourseName("Math", connection);

        verify(connection).prepareStatement(anyString());
        verify(statement).setString(eq(1), eq("Math"));
        verify(statement).executeQuery();

        assertTrue(students.isEmpty());
    }
}
