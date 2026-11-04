package com.napier.sem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

 class MainTest {

    // Test 1: Employee list is null
    @Test
    void printSalariesTestNull() {
        assertDoesNotThrow(() -> Main.printSalaries(null));
    }

    // Test 2: Employee list is empty
    @Test
    void printSalariesTestEmpty() {
        List<Employee> employees = new ArrayList<>();

        assertDoesNotThrow(() -> Main.printSalaries(employees));
    }

    // Test 3: Employee list contains a null employee
    @Test
    void printSalariesTestContainsNull() {
        List<Employee> employees = new ArrayList<>();
        employees.add(null);

        assertDoesNotThrow(() -> Main.printSalaries(employees));
    }

    // Test 4: Employee list contains a valid employee
    @Test
    void printSalariesTestValidEmployee() {
        List<Employee> employees = new ArrayList<>();

        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;

        employees.add(emp);

        assertDoesNotThrow(() -> Main.printSalaries(employees));
    }

    // Test 5: Employee is null
    @Test
    void displayEmployeeTestNull() {
        assertDoesNotThrow(() -> Main.displayEmployee(null));
    }

    // Test 6: Employee has no department or manager
    @Test
    void displayEmployeeTestNoDepartmentOrManager() {
        Employee employee = new Employee();

        employee.emp_no = 1;
        employee.first_name = "Kevin";
        employee.last_name = "Chalmers";
        employee.salary = 55000;
        employee.title = "Engineer";

        assertDoesNotThrow(() -> Main.displayEmployee(employee));
    }

    // Test 7: Employee has a department but no manager
    @Test
    void displayEmployeeTestWithDepartment() {
        Employee employee = new Employee();

        employee.emp_no = 2;
        employee.first_name = "Sarah";
        employee.last_name = "Williams";
        employee.salary = 60000;
        employee.title = "Developer";

        Department department = new Department();
        department.dept_no = "d001";
        department.dept_name = "Development";

        employee.dept = department;

        assertDoesNotThrow(() -> Main.displayEmployee(employee));
    }

    // Test 8: Employee has both department and manager
    @Test
    void displayEmployeeTestWithDepartmentAndManager() {
        Employee employee = new Employee();

        employee.emp_no = 3;
        employee.first_name = "James";
        employee.last_name = "Brown";
        employee.salary = 65000;
        employee.title = "Senior Developer";

        Department department = new Department();
        department.dept_no = "d002";
        department.dept_name = "IT";

        employee.dept = department;

        Employee manager = new Employee();
        manager.emp_no = 10;
        manager.first_name = "Michael";
        manager.last_name = "Smith";

        employee.manager = manager;

        assertDoesNotThrow(() -> Main.displayEmployee(employee));
    }

    // Test 9: Markdown report contains valid employee information
    @Test
    void generateMarkdownReportValidEmployee() {

        List<Employee> employees = new ArrayList<>();

        Employee employee = new Employee();
        employee.emp_no = 10001;
        employee.first_name = "Kevin";
        employee.last_name = "Chalmers";
        employee.salary = 55000;

        employees.add(employee);

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertAll("Verify Markdown salary report for a valid employee",

                () -> assertTrue(
                        markdown.contains("# Employee Salary Report"),
                        "Markdown report should contain the Employee Salary Report heading"
                ),

                () -> assertTrue(
                        markdown.contains(
                                "| 10001 | Kevin | Chalmers | 55000 |"
                        ),
                        "Markdown report should contain Kevin Chalmers' employee details and salary"
                )
        );
    }
    // Test 10: Markdown report handles an empty employee list
    @Test
    void generateMarkdownReportEmptyList() {

        List<Employee> employees = new ArrayList<>();

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertTrue(markdown.contains(
                "No employee records available"
        ), "Markdown report should display a message when no employee records are available");
    }

    // Test 11: Markdown report handles a null employee list
    @Test
    void generateMarkdownReportNullList() {

        String markdown = Main.generateMarkdownSalaryReport(null);

        assertTrue(markdown.contains(
                "No employee records available"
        ), "Markdown report should display a message when no employee records are available");
    }

    // Test 12: Markdown report skips null employee records
    @Test
    void generateMarkdownReportContainsNullEmployee() {

        List<Employee> employees = new ArrayList<>();

        // Add a null employee to test how the report handles it
        employees.add(null);

        // Create a valid employee
        Employee employee = new Employee();
        employee.emp_no = 10002;
        employee.first_name = "Sarah";
        employee.last_name = "Williams";
        employee.salary = 60000;

        employees.add(employee);

        // Generate the Markdown salary report
        String markdown = Main.generateMarkdownSalaryReport(employees);

        // Group all assertions into one logical test
        assertAll("Verify Markdown report handles null employee records",

                () -> assertTrue(
                        markdown.contains(
                                "| 10002 | Sarah | Williams | 60000 |"
                        ),
                        "Markdown report should contain Sarah Williams' employee details and salary"
                ),

                () -> assertFalse(
                        markdown.contains("No employee records available"),
                        "Markdown report should not display the no-records message when employee records exist"
                ),

                () -> assertEquals(
                        1,
                        markdown.lines()
                                .filter(line -> line.startsWith("| 10002 |"))
                                .count(),
                        "Employee 10002 should appear exactly once in the Markdown report"
                )
        );
    }
    // Test 13: Markdown report contains the correct table headings
    @Test
    void generateMarkdownReportFormatting() {

        List<Employee> employees = new ArrayList<>();

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertAll("Verify Markdown salary report formatting",

                () -> assertTrue(
                        markdown.startsWith("# Employee Salary Report"),
                        "Markdown report should start with the Employee Salary Report heading"
                ),

                () -> assertTrue(
                        markdown.contains(
                                "| Employee Number | First Name | Last Name | Salary |"
                        ),
                        "Markdown report should contain the correct table column headings"
                ),

                () -> assertTrue(
                        markdown.contains(
                                "|-----------------|------------|-----------|--------|"
                        ),
                        "Markdown report should contain the correct table separator"
                )
        );
    }


}