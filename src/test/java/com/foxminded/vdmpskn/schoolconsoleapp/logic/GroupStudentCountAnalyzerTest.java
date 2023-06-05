package com.foxminded.vdmpskn.schoolconsoleapp.logic;

import com.foxminded.vdmpskn.schoolconsoleapp.dao.DatabaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class GroupStudentCountAnalyzerTest {
    @Mock
    private DatabaseConnector mockConnector;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private GroupStudentCountAnalyzer analyzer;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);
        analyzer = new GroupStudentCountAnalyzer(mockConnector);
        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void findGroupsWithMaxStudents_ShouldPrintGroupInfo_WhenStudentsCountIsLessOrEqual() throws SQLException {
        int maxStudents = 10;
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("group_id")).thenReturn(1, 2);
        when(mockResultSet.getString("group_name")).thenReturn("Group 1", "Group 2");
        when(mockResultSet.getInt("student_count")).thenReturn(5, 8);

        analyzer.findGroupsWithMaxStudents(maxStudents);

        verify(mockStatement).setInt(1, maxStudents);
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(2)).getInt("group_id");
        verify(mockResultSet, times(2)).getString("group_name");
        verify(mockResultSet, times(2)).getInt("student_count");
    }

    @Test
    public void findGroupsWithMaxStudents_ShouldNotPrintGroupInfo_WhenStudentsCountIsGreater() throws SQLException {
        int maxStudents = 10;
        when(mockResultSet.next()).thenReturn(false);

        analyzer.findGroupsWithMaxStudents(maxStudents);

        verify(mockStatement).setInt(1, maxStudents);
        verify(mockResultSet).next();
    }
}

