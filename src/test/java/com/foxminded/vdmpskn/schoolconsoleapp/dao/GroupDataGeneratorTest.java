package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GroupDataGeneratorTest {
    @Mock
    private DatabaseConnector mockConnector;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;

    private GroupDataGenerator dataGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        dataGenerator = spy(new GroupDataGenerator(mockConnector));
    }

    @Test
    void generateAndInsertGroups_ShouldExecuteBatchSuccessfully() throws SQLException {

        List<String> groupNames = new ArrayList<>();
        groupNames.add("AB-12");
        groupNames.add("CD-34");
        doReturn(groupNames).when(dataGenerator).generateRandomGroupNames(GroupDataGenerator.NUM_GROUPS);

        dataGenerator.generateAndInsertGroups();

        verify(mockConnector).getConnection();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockStatement, times(2)).setString(eq(1), anyString());
        verify(mockStatement, times(2)).addBatch();
        verify(mockStatement).executeBatch();
        verify(mockStatement).close();
        verify(mockConnection).close();
    }
}

