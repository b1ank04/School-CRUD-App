package com.foxminded.school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String url = "jdbc:postgresql://localhost:5432/school_db";
    private static final String user = "postgres";
    private static final String pass = "1234";

    private Database() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

}
