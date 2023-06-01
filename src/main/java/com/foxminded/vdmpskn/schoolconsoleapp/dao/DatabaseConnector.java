package com.foxminded.vdmpskn.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String url = "jdbc:postgresql://localhost:5432/schooldb";
    private final String username = "admin";
    private final String password = "1111";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
