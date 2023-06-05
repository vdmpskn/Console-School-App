package com.foxminded.vdmpskn.spring.schoolconsoleapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SQLRunner {
    private static final Log log = LogFactory.getLog(SQLRunner.class);

    private final DatabaseConnector connector;

    public SQLRunner(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void runTableCreationScript(String TABLES_SCRIPT_FILE) throws SQLException {
        boolean tablesExist = checkTablesExist();
        if (tablesExist) {
            dropTables();
            log.info("Existing tables dropped.");
        }

        String script = readScriptFile(TABLES_SCRIPT_FILE);
        executeScript(script);
        log.info("Table creation script executed successfully.");
    }

    public boolean checkTablesExist() throws SQLException {

        String query = "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'groups')";
        Statement statement = null;
        ResultSet resultSet = null;
        try(Connection connection = connector.getConnection();) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void dropTables() throws SQLException {
        Statement statement = null;
        try(Connection connection = connector.getConnection();) {
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS students CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS courses CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS groups CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS student_courses CASCADE");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String readScriptFile(String fileName) {
        StringBuilder script = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line);
                script.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return script.toString();
    }

    public void executeScript(String script) throws SQLException {
        Statement statement = null;
        try(Connection connection = connector.getConnection();) {
            statement = connection.createStatement();
            statement.executeUpdate(script);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
