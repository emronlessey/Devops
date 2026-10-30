package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class MainIntegrationTest {

    static Connection connection;

    @BeforeAll
    static void init() throws Exception {

        String url = "jdbc:mysql://localhost:3307/employees"
                + "?allowPublicKeyRetrieval=true&useSSL=false";

        connection = DriverManager.getConnection(
                url,
                "root",
                "example"
        );

        assertNotNull(connection);
        assertFalse(connection.isClosed());

        System.out.println("Successfully connected to MySQL for integration testing!");
    }

    @Test
    void testGetEmployee() {

        Employee employee = Main.getEmployee(connection, 10001);

        assertNotNull(employee, "Employee should exist in the database");

        assertEquals(10001, employee.emp_no);
        assertEquals("Georgi", employee.first_name);
        assertEquals("Facello", employee.last_name);
    }
    @Test
    void testGetEmployeeNotFound() {

        Employee employee = Main.getEmployee(connection, -1);

        assertNull(
                employee,
                "An employee with an invalid number should not exist"
        );
    }
    @Test
    void testGetEmployeeByName() {

        Employee employee = Main.getEmployee(
                connection,
                "Georgi",
                "Facello"
        );

        assertNotNull(
                employee,
                "Employee should exist in the database"
        );

        assertEquals(10001, employee.emp_no);
        assertEquals("Georgi", employee.first_name);
        assertEquals("Facello", employee.last_name);
    }

    @Test
    void testGetDepartment() {

        Department department = Main.getDepartment(
                connection,
                "Sales"
        );

        assertNotNull(
                department,
                "Sales department should exist in the database"
        );

    }

    @Test
    void testGetDepartmentNotFound() {

        Department department = Main.getDepartment(
                connection,
                "NonExistentDepartment"
        );

        assertNull(
                department,
                "A department that does not exist should return null"
        );
    }
    @Test
    void testGetSalariesByDepartment() {

        Department department = Main.getDepartment(
                connection,
                "Sales"
        );

        assertNotNull(
                department,
                "Sales department should exist"
        );

        ArrayList<Employee> employees =
                Main.getSalariesByDepartment(connection, department);

        assertNotNull(
                employees,
                "Salary retrieval should not return null"
        );

        assertFalse(
                employees.isEmpty(),
                "Sales department should contain employee salary records"
        );
    }

    @Test
    void testGetAllSalaries() {

        ArrayList<Employee> employees =
                Main.getAllSalaries(connection);

        assertNotNull(
                employees,
                "The salary list should not be null"
        );

        assertFalse(
                employees.isEmpty(),
                "The database should contain employee salary records"
        );
    }

    @Test
    void testGetSalariesByRole() {

        assertDoesNotThrow(
                () -> Main.getSalariesByRole(
                        connection,
                        "Engineer"
                ),
                "Salary retrieval by role should execute without errors"
        );
    }

    @Test
    void testAddEmployee() throws Exception {

        int employeeNumber = 999999;

        boolean originalAutoCommit = connection.getAutoCommit();

        try {
            // Start a transaction
            connection.setAutoCommit(false);

            // Add a temporary employee
            boolean employeeAdded = Main.addEmployee(
                    connection,
                    employeeNumber,
                    "Test",
                    "Employee",
                    "M",
                    "1990-01-01",
                    "2026-01-01"
            );

            // Verify that the employee was added
            assertTrue(
                    employeeAdded,
                    "Employee should be added successfully"
            );

            // Verify the employee exists in the database
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT first_name, last_name FROM employees WHERE emp_no = ?")) {

                statement.setInt(1, employeeNumber);

                try (ResultSet result = statement.executeQuery()) {

                    assertTrue(
                            result.next(),
                            "New employee should exist in the database"
                    );

                    assertEquals("Test", result.getString("first_name"));
                    assertEquals("Employee", result.getString("last_name"));
                }
            }

        } finally {
            // Remove the temporary employee by rolling back
            try {
                connection.rollback();
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        }
    }

    @Test
    void testAddEmployeeInvalidData() {

        boolean result = Main.addEmployee(
                connection,
                -1,
                "",
                "Employee",
                "M",
                "1990-01-01",
                "2026-01-01"
        );

        assertFalse(
                result,
                "Invalid employee information should be rejected"
        );
    }

    @AfterAll
    static void cleanup() throws Exception {

        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Integration test database connection closed.");
        }
    }
}
