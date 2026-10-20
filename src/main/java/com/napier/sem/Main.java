package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        String host = System.getenv().getOrDefault("MYSQL_HOST", "localhost");
        String port = System.getenv().getOrDefault("MYSQL_PORT", "3307");
        String database = System.getenv().getOrDefault("MYSQL_DATABASE", "employees");
        String user = System.getenv().getOrDefault("MYSQL_USER", "root");
        String password = System.getenv().getOrDefault("MYSQL_PASSWORD", "example");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(
                     "SELECT COUNT(*) AS total_employees FROM employees")) {

            System.out.println("Successfully connected to MySQL!");

            if (result.next()) {
                System.out.println("Total employees: " + result.getInt("total_employees"));
            }

        } catch (Exception e) {
            System.err.println("MySQL connection failed: " + e.getMessage());

            // Ensure GitHub Actions detects the failure
            System.exit(1);
        }
    }
}