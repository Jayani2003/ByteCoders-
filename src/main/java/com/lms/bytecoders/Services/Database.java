package com.lms.bytecoders.Services;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    public static Connection Conn() {

        Dotenv dotenv = Dotenv.configure().load();

        String host = dotenv.get("DATABASE_HOST");
        String port = dotenv.get("DATABASE_PORT");
        String databaseName = dotenv.get("DATABASE_NAME");
        String userName = dotenv.get("DATABASE_USERNAME");
        String password = dotenv.get("DATABASE_PASSWORD");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

        return conn;
    }
}