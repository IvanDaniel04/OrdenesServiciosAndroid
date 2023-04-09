package com.example.ordenesserviciosandroid.enlaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EnlaceIvan {
    private static final String URL = "jdbc:mysql://localhost:3306/ordenesservicios";
    private static final String USER = "root";
    private static final String PASSWORD = "Stephen2002";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
