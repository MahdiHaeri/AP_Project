package com.example.server.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ap_project";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mobinz16";

    private static Connection connection;

    private DatabaseConnectionManager() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
        return connection;
    }
}
