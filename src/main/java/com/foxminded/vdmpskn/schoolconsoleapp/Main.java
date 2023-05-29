package com.foxminded.vdmpskn.schoolconsoleapp;

public class Main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/schooldb";
    private static final String USER = "admin";
    private static final String PASSWORD = "1111";
    private static final String TABLES_SCRIPT_FILE = "src/main/resources/create_tables.sql";


    public static void main(String[] args) {
        SQLRunner.runTableCreationScript(DB_URL,USER,PASSWORD,TABLES_SCRIPT_FILE);
    }

}