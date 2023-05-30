package com.foxminded.vdmpskn.schoolconsoleapp.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupStudentCountAnalyzerTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @Test
    public void testFindGroupsWithMaxStudents() throws SQLException {
        int maxStudents = 30;

        String expectedSql = "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) AS student_count "
                + "FROM groups "
                + "LEFT JOIN students ON groups.group_id = students.group_id "
                + "GROUP BY groups.group_id, groups.group_name "
                + "HAVING COUNT(students.student_id) <= ?";


        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // Simulate one row in the result set
        when(mockResultSet.getInt("group_id")).thenReturn(1);
        when(mockResultSet.getString("group_name")).thenReturn("IR-21");
        when(mockResultSet.getInt("student_count")).thenReturn(25);


        when(mockConnection.prepareStatement(expectedSql)).thenReturn(mockStatement);


        GroupStudentCountAnalyzer.findGroupsWithMaxStudents(mockConnection, maxStudents);


        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockStatement).setInt(1, maxStudents);
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(2)).next(); // One for checking while loop condition, one for reading row
        verify(mockResultSet).getInt("group_id");
        verify(mockResultSet).getString("group_name");
        verify(mockResultSet).getInt("student_count");

    }
}
