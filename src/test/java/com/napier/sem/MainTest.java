package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    // Test 1: Employee list is null
    @Test
    void printSalariesTestNull() {
        assertDoesNotThrow(() -> Main.printSalaries(null));
    }

    // Test 2: Employee list is empty
    @Test
    void printSalariesTestEmpty() {
        ArrayList<Employee> employees = new ArrayList<>();

        assertDoesNotThrow(() -> Main.printSalaries(employees));
    }

    // Test 3: Employee list contains a null employee
    @Test
    void printSalariesTestContainsNull() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(null);

        assertDoesNotThrow(() -> Main.printSalaries(employees));
    }

    // Test 4: Employee list contains a valid employee
    @Test
    void printSalariesTestValidEmployee() {
        ArrayList<Employee> employees = new ArrayList<>();

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

        ArrayList<Employee> employees = new ArrayList<>();

        Employee employee = new Employee();
        employee.emp_no = 10001;
        employee.first_name = "Kevin";
        employee.last_name = "Chalmers";
        employee.salary = 55000;

        employees.add(employee);

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertTrue(markdown.contains("# Employee Salary Report"));
        assertTrue(markdown.contains(
                "| 10001 | Kevin | Chalmers | 55000 |"
        ));
    }

    // Test 10: Markdown report handles an empty employee list
    @Test
    void generateMarkdownReportEmptyList() {

        ArrayList<Employee> employees = new ArrayList<>();

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertTrue(markdown.contains(
                "No employee records available"
        ));
    }

    // Test 11: Markdown report handles a null employee list
    @Test
    void generateMarkdownReportNullList() {

        String markdown = Main.generateMarkdownSalaryReport(null);

        assertTrue(markdown.contains(
                "No employee records available"
        ));
    }

    // Test 12: Markdown report skips null employee records
    @Test
    void generateMarkdownReportContainsNullEmployee() {

        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(null);

        Employee employee = new Employee();
        employee.emp_no = 10002;
        employee.first_name = "Sarah";
        employee.last_name = "Williams";
        employee.salary = 60000;

        employees.add(employee);

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertTrue(markdown.contains(
                "| 10002 | Sarah | Williams | 60000 |"
        ));

        assertFalse(markdown.contains(
                "No employee records available"
        ));

        assertEquals(1, markdown.lines()
                .filter(line -> line.startsWith("| 10002 |"))
                .count());
    }

    // Test 13: Markdown report contains the correct table headings
    @Test
    void generateMarkdownReportFormatting() {

        ArrayList<Employee> employees = new ArrayList<>();

        String markdown = Main.generateMarkdownSalaryReport(employees);

        assertTrue(markdown.startsWith(
                "# Employee Salary Report"
        ));

        assertTrue(markdown.contains(
                "| Employee Number | First Name | Last Name | Salary |"
        ));

        assertTrue(markdown.contains(
                "|-----------------|------------|-----------|--------|"
        ));
    }
}