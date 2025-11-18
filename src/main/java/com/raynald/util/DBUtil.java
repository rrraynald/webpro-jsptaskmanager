package com.raynald.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    
    private static final String URL = "jdbc:mysql://localhost:3306/jsp_task_manager";
    private static final String USER = "root"; 
    private static final String PASS = "0000";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver!");
        }
    }
    
    /**
     * Gets a connection to the database
     * @return A Connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // public static Connection getConnection() throws SQLException {
    //     System.out.println("--- DBUtil: Attempting to connect to database... ---");
    //     try {
    //         Connection conn = DriverManager.getConnection(URL, USER, PASS);
    //         System.out.println("--- DBUtil: Connection SUCCESSFUL! ---");
    //         return conn;
    //     } catch (SQLException e) {
    //         System.out.println("--- DBUtil: Connection FAILED: " + e.getMessage() + " ---");
    //         throw e;
    //     }
    // }
}