package com.foxminded.vdmpskn.spring.schoolconsoleapp;

import com.foxminded.vdmpskn.spring.schoolconsoleapp.config.StudentsToCourseAssigner;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.config.StudentsToGroupAssigner;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.dao.*;
import com.foxminded.vdmpskn.spring.schoolconsoleapp.front.ConsoleMenu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;

public class SchoolConsoleApp {
    private static final String tableScriptFile = "src/main/resources/create_tables.sql";
    private static final Log log = LogFactory.getLog(SchoolConsoleApp.class);
    private static DatabaseConnector connector = new DatabaseConnector();

    public SchoolConsoleApp(DatabaseConnector connector) {
        this.connector = connector;
    }

    public static void main(String[] args) throws SQLException {

        log.info("Connected to the database");

       SQLRunner sqlRunner = new SQLRunner(connector);
       sqlRunner.runTableCreationScript(tableScriptFile);//SQL script with table creation from previously created files

        GroupDataGenerator groupDataGenerator = new GroupDataGenerator(connector);
        groupDataGenerator.generateAndInsertGroups(); //10 groups with randomly generated names.

        CoursesDataGenerator coursesDataGenerator = new CoursesDataGenerator(connector);
        coursesDataGenerator.createCourse(); //Create 10 courses (math, biology, etc)

        StudentsDataGenerator studentsDataGenerator = new StudentsDataGenerator(connector);
        studentsDataGenerator.generateStudents();//200 students

        StudentsToGroupAssigner studentsToGroupAssigner = new StudentsToGroupAssigner(connector);
        studentsToGroupAssigner.assignStudentsToGroups(); //Randomly assign students to groups.

        StudentsToCourseAssigner studentsToCourseAssigner = new StudentsToCourseAssigner(connector);
        studentsToCourseAssigner.assignCoursesToStudents();//

        ConsoleMenu consoleMenu = new ConsoleMenu(connector);
        consoleMenu.getConsole(); // Console-Menu
    }

}


