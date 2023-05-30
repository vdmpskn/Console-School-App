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

        log.info("Connected to the database");
        SQLRunner.runTableCreationScript(TABLES_SCRIPT_FILE);//SQL script with table creation from previously created files
//        GroupDataGenerator.generateAndInsertGroups();//10 groups with randomly generated names.
       //CoursesDataGenerator.createCourse(); //Create 10 courses (math, biology, etc)
//        StudentsDataGenerator.generateStudents();//200 students
//        StudentsToGroupAssigner.assignStudentsToGroups(); //Randomly assign students to groups.
//        StudentsToCourseAssigner.assignCoursesToStudents();//Create the MANY-TO-MANY relation  between STUDENTS and COURSES tables
//        ConsoleMenu.getConsole(); // Console-Menu

    }

}


