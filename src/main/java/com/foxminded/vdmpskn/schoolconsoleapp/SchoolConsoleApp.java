package com.foxminded.vdmpskn.schoolconsoleapp;

import com.foxminded.vdmpskn.schoolconsoleapp.config.StudentsToCourseAssigner;
import com.foxminded.vdmpskn.schoolconsoleapp.config.StudentsToGroupAssigner;
import com.foxminded.vdmpskn.schoolconsoleapp.dao.*;
import com.foxminded.vdmpskn.schoolconsoleapp.front.ConsoleMenu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.sql.*;


public class SchoolConsoleApp {
    private static final String TABLES_SCRIPT_FILE = "src/main/resources/create_tables.sql";
    private static final Log log = LogFactory.getLog(SchoolConsoleApp.class);

    public static void main(String[] args) throws SQLException {
        DatabaseConnector connector = new DatabaseConnector();

        try (Connection connection = connector.getConnection()) {

            log.info("Connected to the database");

            SQLRunner.runTableCreationScript(connection, TABLES_SCRIPT_FILE);//SQL script with table creation from previously created files

            GroupDataGenerator.generateAndInsertGroups(connection);//10 groups with randomly generated names.

            CoursesDataGenerator.createCourse(connection); //Create 10 courses (math, biology, etc)

            StudentsDataGenerator.generateStudents(connection);//200 students

            StudentsToGroupAssigner.assignStudentsToGroups(connection); //Randomly assign students to groups.

            StudentsToCourseAssigner.assignCoursesToStudents(connection);//Create the MANY-TO-MANY relation  between STUDENTS and COURSES tables

            ConsoleMenu.getConsole(connection); // Console-Menu

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    }


