package com.foxminded.vdmpskn.schoolconsoleapp.manage;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class StudentManagerTest {

    @Test
    void testAddNewStudent() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        StudentManager.addNewStudent(connection, "John", "Doe");

        verify(connection).prepareStatement(anyString());
        verify(statement).setString(1, "John");
        verify(statement).setString(2, "Doe");
        verify(statement).executeUpdate();
    }

    @Test
    void testDeleteStudent() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement deleteStudentCoursesStatement = mock(PreparedStatement.class);
        PreparedStatement deleteStudentStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(deleteStudentCoursesStatement)
                .thenReturn(deleteStudentStatement);
        when(deleteStudentCoursesStatement.executeUpdate()).thenReturn(1);
        when(deleteStudentStatement.executeUpdate()).thenReturn(1);

        StudentManager.deleteStudent(connection, 123);

        verify(connection, times(2)).prepareStatement(anyString());
        verify(deleteStudentCoursesStatement).setInt(anyInt(), anyInt());
        verify(deleteStudentCoursesStatement).executeUpdate();
        verify(deleteStudentStatement).setInt(anyInt(), anyInt());
        verify(deleteStudentStatement).executeUpdate();
    }
}
