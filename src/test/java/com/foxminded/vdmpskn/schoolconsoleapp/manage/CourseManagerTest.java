package com.foxminded.vdmpskn.schoolconsoleapp.manage;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CourseManagerTest {

    @Mock
    private DatabaseConnector mockConnector;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private CourseManager courseManager;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        courseManager = new CourseManager(mockConnector);

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(anyString())).thenReturn("Course 1");
    }

    @Test
    void addStudentToCourse_ShouldPrintSuccessMessage_WhenStudentAddedSuccessfully() throws SQLException {
        int studentId = 1;
        int courseId = 1;

        courseManager.addStudentToCourse(studentId, courseId);

        verify(mockStatement).setInt(1, studentId);
        verify(mockStatement).setInt(2, courseId);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getStudentCourses_ShouldReturnListOfCourseNames_WhenStudentHasCourses() throws SQLException {
        int studentId = 1;

        List<String> result = courseManager.getStudentCourses(studentId);

        verify(mockStatement).setInt(1, studentId);
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
        verify(mockResultSet).getString("course_name");
    }

    @Test
    void removeStudentFromCourse_ShouldPrintSuccessMessage_WhenStudentRemovedSuccessfully() throws SQLException {
        int studentId = 1;
        String courseName = "Course 1";

        courseManager.removeStudentFromCourse(studentId, courseName);

        verify(mockStatement).setInt(1, studentId);
        verify(mockStatement).setString(2, courseName);
        verify(mockStatement).executeUpdate();
    }
}
