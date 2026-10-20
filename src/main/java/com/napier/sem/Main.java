package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
            // Retrieve employee 10001
            Employee employee = getEmployee(connection, 10001);

// Display employee details
            displayEmployee(employee);

        } catch (Exception e) {
            System.err.println("MySQL connection failed:");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static Employee getEmployee(Connection connection, int employeeNumber) {

        String sql = """
    SELECT
        e.emp_no,
        e.first_name,
        e.last_name,
        e.gender,
        e.birth_date,
        e.hire_date,
        s.salary,
        t.title,
        d.dept_name AS department,
        CONCAT(m.first_name, ' ', m.last_name) AS manager
    FROM employees e
    LEFT JOIN salaries s
        ON e.emp_no = s.emp_no
        AND s.to_date = '9999-01-01'
    LEFT JOIN titles t
        ON e.emp_no = t.emp_no
        AND t.to_date = '9999-01-01'
    LEFT JOIN dept_emp de
        ON e.emp_no = de.emp_no
        AND de.to_date = '9999-01-01'
    LEFT JOIN departments d
        ON de.dept_no = d.dept_no
    LEFT JOIN dept_manager dm
        ON de.dept_no = dm.dept_no
        AND dm.to_date = '9999-01-01'
    LEFT JOIN employees m
        ON dm.emp_no = m.emp_no
    WHERE e.emp_no = ?
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Employee employee = new Employee();

                    employee.emp_no = result.getInt("emp_no");
                    employee.first_name = result.getString("first_name");
                    employee.last_name = result.getString("last_name");
                    employee.gender = result.getString("gender");
                    employee.birth_date = result.getString("birth_date");
                    employee.hire_date = result.getString("hire_date");


                    // Additional employee information
                    employee.salary = result.getInt("salary");
                    employee.title = result.getString("title");
                    employee.department = result.getString("department");
                    employee.manager = result.getString("manager");

                    return employee;
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
        }

        return null;
    }
    public static void displayEmployee(Employee employee) {

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("\nEmployee Details:");
        System.out.println("Employee Number: " + employee.emp_no);
        System.out.println("First Name: " + employee.first_name);
        System.out.println("Last Name: " + employee.last_name);
        System.out.println("Gender: " + employee.gender);
        System.out.println("Birth Date: " + employee.birth_date);
        System.out.println("Hire Date: " + employee.hire_date);

        // Display additional employee information
        System.out.println("Salary: " + employee.salary);
        System.out.println("Job Title: " + employee.title);
        System.out.println("Department: " + employee.department);
        System.out.println("Manager: " + employee.manager);

    }
}