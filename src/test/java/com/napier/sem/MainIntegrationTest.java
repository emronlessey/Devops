package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class MainIntegrationTest {

    private static final Logger LOGGER =
            Logger.getLogger(MainIntegrationTest.class.getName());

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

        assertNotNull(connection,
                "Database connection should not be null");

        assertFalse(connection.isClosed(),
                "Database connection should remain open during integration testing");

        LOGGER.info("Successfully connected to MySQL for integration testing!");
    }

    @Test
    void testGetEmployee() {

        Employee employee = Main.getEmployee(connection, 10001);

        assertAll("Verify employee 10001 details",

                () -> assertNotNull(employee,
                        "Employee should exist in the database"),

                () -> {
                    assertNotNull(employee,
                            "Employee must exist before checking its details");

                    assertEquals(10001, employee.emp_no,
                            "Employee number should be 10001");

                    assertEquals("Georgi", employee.first_name,
                            "Employee first name should be Georgi");

                    assertEquals("Facello", employee.last_name,
                            "Employee last name should be Facello");
                }
        );
    }
    @Test
    void testGetEmployeeByName() {

        Employee employee = Main.getEmployee(
                connection,
                "Georgi",
                "Facello"
        );

        assertAll("Verify employee details retrieved by name",

                () -> assertNotNull(employee,
                        "Employee should exist in the database"),

                () -> {
                    if (employee != null) {

                        assertEquals(10001, employee.emp_no,
                                "Employee number should be 10001");

                        assertEquals("Georgi", employee.first_name,
                                "Employee first name should be Georgi");

                        assertEquals("Facello", employee.last_name,
                                "Employee last name should be Facello");
                    }
                }
        );
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

        List<Employee> employees = department == null
                ? null
                : Main.getSalariesByDepartment(connection, department);

        assertAll("Verify Sales department salary records",

                () -> assertNotNull(
                        department,
                        "Sales department should exist"
                ),

                () -> assertNotNull(
                        employees,
                        "Salary retrieval should not return null"
                ),

                () -> assertTrue(
                        employees != null && !employees.isEmpty(),
                        "Sales department should contain employee salary records"
                )
        );
    }
    @Test
    void testGetAllSalaries() {

        List<Employee> employees =
                Main.getAllSalaries(connection);

        assertAll("Verify employee salary records",

                () -> assertNotNull(
                        employees,
                        "The salary list should not be null"
                ),

                () -> assertFalse(
                        employees == null || employees.isEmpty(),
                        "The database should contain employee salary records"
                )
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

            // Retrieve the inserted employee for verification
            boolean employeeExists = false;
            String firstName = null;
            String lastName = null;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 "SELECT first_name, last_name " +
                                         "FROM employees WHERE emp_no = ?")) {

                statement.setInt(1, employeeNumber);

                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        employeeExists = true;
                        firstName = result.getString("first_name");
                        lastName = result.getString("last_name");
                    }
                }
            }

            // Capture final values for JUnit lambda expressions
            final boolean added = employeeAdded;
            final boolean exists = employeeExists;
            final String actualFirstName = firstName;
            final String actualLastName = lastName;

            // Group all related checks into one assertion
            assertAll("Verify employee insertion and database details",

                    () -> assertTrue(
                            added,
                            "Employee should be added successfully"
                    ),

                    () -> assertTrue(
                            exists,
                            "New employee should exist in the database"
                    ),

                    () -> assertEquals(
                            "Test",
                            actualFirstName,
                            "Employee first name should be Test"
                    ),

                    () -> assertEquals(
                            "Employee",
                            actualLastName,
                            "Employee last name should be Employee"
                    )
            );

        } finally {

            // Roll back the temporary employee insertion
            try {
                connection.rollback();
            } finally {
                // Restore the original auto-commit setting
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

    @Test
    void testMainApplicationExecution() {

        assertDoesNotThrow(
                () -> Main.main(new String[]{}),
                "Main application should execute successfully"
        );
    }

    @AfterAll
    static void cleanup() throws Exception {

        if (connection != null && !connection.isClosed()) {
            connection.close();
            LOGGER.info("Integration test database connection closed.");
        }
    }
}
