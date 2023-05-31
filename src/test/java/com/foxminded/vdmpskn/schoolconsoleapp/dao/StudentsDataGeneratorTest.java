package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;
import com.foxminded.vdmpskn.schoolconsoleapp.dao.StudentsDataGenerator;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StudentsDataGeneratorTest {

    @Mock
    private DatabaseConnector mockConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private StudentsDataGenerator dataGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt(anyString())).thenReturn(1);

        dataGenerator = new StudentsDataGenerator(mockConnector);
    }

    @Test
    void generateStudents_ShouldThrowException_WhenNoGroupIdsAvailable() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> dataGenerator.generateStudents());
    }
}




