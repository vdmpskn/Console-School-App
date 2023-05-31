package com.foxminded.vdmpskn.schoolconsoleapp.manage;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StudentManagerTest {

    @Mock
    private DatabaseConnector mockConnector;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;

    private StudentManager studentManager;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        studentManager = new StudentManager(mockConnector);

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
    }

    @Test
    void addNewStudent_ShouldPrintSuccessMessage_WhenStudentAddedSuccessfully() throws SQLException {
        String firstName = "John";
        String lastName = "Doe";

        studentManager.addNewStudent(firstName, lastName);

        verify(mockStatement).setString(1, firstName);
        verify(mockStatement).setString(2, lastName);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void deleteStudent_ShouldPrintSuccessMessage_WhenStudentDeletedSuccessfully() throws SQLException {
        int studentId = 1;

        studentManager.deleteStudent(studentId);

        verify(mockStatement, times(2)).setInt(1, studentId);
        verify(mockStatement, times(2)).executeUpdate();
    }
}
