package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;

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


    @AfterAll
    static void cleanup() throws Exception {

        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Integration test database connection closed.");
        }
    }
}