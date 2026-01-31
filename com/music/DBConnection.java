package com.music;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Musiclibrary";
    private static final String USER = "postgres";
    private static final String PASS = "beka2007";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}