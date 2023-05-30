package com.foxminded.vdmpskn.schoolconsoleapp.manage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseManagerTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Test
    void testAddStudentToCourse() throws SQLException {
        int studentId = 1;
        int courseId = 2;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        CourseManager.addStudentToCourse(connection, studentId, courseId);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setInt(1, studentId);
        verify(preparedStatement, times(1)).setInt(2, courseId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testGetStudentCourses() throws SQLException {
        int studentId = 1;
        List<String> expectedCourseNames = Arrays.asList("Math", "Science");

        when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("course_name")).thenReturn("Math", "Science");

        List<String> actualCourseNames = CourseManager.getStudentCourses(connection, studentId);

        assertEquals(expectedCourseNames, actualCourseNames);

        verify(connection, times(1)).prepareStatement(Mockito.anyString());
        verify(preparedStatement, times(1)).setInt(1, studentId);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getString("course_name");
    }

    @Test
    void testRemoveStudentFromCourse() throws SQLException {
        int studentId = 1;
        String courseName = "Math";

        when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);

        CourseManager.removeStudentFromCourse(connection, studentId, courseName);

        verify(connection, times(1)).prepareStatement(Mockito.anyString());
        verify(preparedStatement, times(1)).setInt(1, studentId);
        verify(preparedStatement, times(1)).setString(2, courseName);
        verify(preparedStatement, times(1)).executeUpdate();
    }
}

