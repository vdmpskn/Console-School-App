package com.foxminded.vdmpskn.schoolconsoleapp;

import com.foxminded.vdmpskn.schoolconsoleapp.config.StudentsToCourseAssigner;
import com.foxminded.vdmpskn.schoolconsoleapp.config.StudentsToGroupAssigner;
import com.foxminded.vdmpskn.schoolconsoleapp.dao.SQLRunner;
import com.foxminded.vdmpskn.schoolconsoleapp.front.ConsoleMenu;
import com.foxminded.vdmpskn.schoolconsoleapp.dao.CoursesDataGenerator;
import com.foxminded.vdmpskn.schoolconsoleapp.dao.GroupDataGenerator;
import com.foxminded.vdmpskn.schoolconsoleapp.dao.StudentsDataGenerator;

import java.sql.*;


public class SchoolConsoleApp {
   private static final String DB_URL = "jdbc:postgresql://localhost:5432/schooldb";
    private static final String USER = "admin";
    private static final String PASSWORD = "1111";
    private static final String TABLES_SCRIPT_FILE = "src/main/resources/create_tables.sql";


    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

            System.out.println("Connected to the database");

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


