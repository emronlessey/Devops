package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
            // Lab 5 - Test retrieving a department by name
            Department department = getDepartment(connection, "Sales");

            if (department != null) {

                System.out.println("\nDepartment Details:");
                System.out.println("Department Number: " + department.dept_no);
                System.out.println("Department Name: " + department.dept_name);

                if (department.manager != null) {

                    System.out.println("Manager Number: " + department.manager.emp_no);

                    System.out.println("Manager Name: "
                            + department.manager.first_name + " "
                            + department.manager.last_name);

                } else {
                    System.out.println("No current department manager found.");
                }

            } else {
                System.out.println("Department not found.");
            }
// Lab 5 - Retrieve salaries for employees in the Sales department
            ArrayList<Employee> departmentEmployees =
                    getSalariesByDepartment(connection, department);

            if (departmentEmployees != null) {

                System.out.println("\nSalary Report for Department: Sales");
                System.out.println("------------------------------------------------------------");

                System.out.printf("%-10s %-15s %-20s %-10s%n",
                        "Emp No", "First Name", "Last Name", "Salary");

                // Display only the first 10 employees
                int limit = Math.min(10, departmentEmployees.size());

                for (int i = 0; i < limit; i++) {

                    Employee emp = departmentEmployees.get(i);

                    System.out.printf("%-10d %-15s %-20s %-10d%n",
                            emp.emp_no,
                            emp.first_name,
                            emp.last_name,
                            emp.salary);
                }

                System.out.println(
                        "\nTotal current employees in Sales: "
                                + departmentEmployees.size());

            } else {
                System.out.println("Failed to retrieve department salaries.");
            }

            // Retrieve employee 10001
            Employee employee = getEmployee(connection, 10001);

// Display employee details
            displayEmployee(employee);

            // Lab 5 - Test retrieving an employee by first and last name
            System.out.println("\nTesting Employee Search by Name");

            Employee employeeByName =
                    getEmployee(connection, "Georgi", "Facello");

            if (employeeByName != null) {

                System.out.println("Employee found successfully!");

                displayEmployee(employeeByName);

            } else {

                System.out.println("Employee not found.");
            }

            // Retrieve and display salaries for employees with the Engineer role
          //  getSalariesByRole(connection, "Engineer");

            // Retrieve all employees with their current salaries
            ArrayList<Employee> employees = getAllSalaries(connection);

// Display the salary report
            if (employees != null) {

                // Print all employee salaries
             //   printSalaries(employees);

                // Display the total number of current salary records
                System.out.println("\nTotal current employee salaries: " + employees.size());

            } else {
                System.out.println("Failed to retrieve employee salaries.");
            }


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
                  //  employee.department = result.getString("department");
                   // employee.manager = result.getString("manager");

                    /// Retrieve the employee's department and department manager
                    String departmentName = result.getString("department");

                    if (departmentName != null) {

                        // Retrieve department details, including its manager
                        employee.dept = getDepartment(connection, departmentName);

                        // Assign the department manager to the employee
                        if (employee.dept != null) {
                            employee.manager = employee.dept.manager;
                        }
                    }

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
        // Display employee department
        if (employee.dept != null) {

            System.out.println("Department Number: " + employee.dept.dept_no);
            System.out.println("Department Name: " + employee.dept.dept_name);

        } else {
            System.out.println("Department: Not assigned");
        }

// Display employee manager
        if (employee.manager != null) {

            System.out.println("Manager Number: " + employee.manager.emp_no);

            System.out.println("Manager Name: "
                    + employee.manager.first_name + " "
                    + employee.manager.last_name);

        } else {
            System.out.println("Manager: Not assigned");
        }
       // System.out.println("Department: " + employee.department);
       // System.out.println("Manager: " + employee.manager);

    }
    public static void getSalariesByRole(Connection connection, String role) {

        String sql = """
        SELECT
            employees.emp_no,
            employees.first_name,
            employees.last_name,
            salaries.salary
        FROM employees, salaries, titles
        WHERE employees.emp_no = salaries.emp_no
          AND employees.emp_no = titles.emp_no
          AND salaries.to_date = '9999-01-01'
          AND titles.to_date = '9999-01-01'
          AND titles.title = ?
        ORDER BY employees.emp_no ASC
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role);

            try (ResultSet result = statement.executeQuery()) {

                System.out.println("\nSalary Report for Role: " + role);
                System.out.println("------------------------------------------------------");

                System.out.printf("%-12s %-20s %-20s %-10s%n",
                        "Emp No", "First Name", "Last Name", "Salary");

                while (result.next()) {

                    System.out.printf("%-12d %-20s %-20s %-10d%n",
                            result.getInt("emp_no"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("salary"));
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving salaries by role: " + e.getMessage());
        }
    }
    /**
     * Retrieves all employees with their current salaries.
     *
     * @param connection Active MySQL database connection.
     * @return List of employees and their current salaries.
     */
    public static ArrayList<Employee> getAllSalaries(Connection connection) {

        ArrayList<Employee> employees = new ArrayList<>();

        String sql = """
            SELECT
                employees.emp_no,
                employees.first_name,
                employees.last_name,
                salaries.salary
            FROM employees, salaries
            WHERE employees.emp_no = salaries.emp_no
              AND salaries.to_date = '9999-01-01'
            ORDER BY employees.emp_no ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Employee employee = new Employee();

                employee.emp_no = result.getInt("emp_no");
                employee.first_name = result.getString("first_name");
                employee.last_name = result.getString("last_name");
                employee.salary = result.getInt("salary");

                employees.add(employee);
            }

        } catch (Exception e) {
            System.err.println("Error retrieving employee salaries: " + e.getMessage());
            return null;
        }

        return employees;
    }
    /**
     * Prints a formatted list of employees and their current salaries.
     *
     * @param employees The list of employees to print.
     */
    public static void printSalaries(ArrayList<Employee> employees) {

        // Check whether the employee list is null
        if (employees == null) {
            System.out.println("No salary information available.");
            return;
        }

        // Print report heading
        System.out.println("\nSalary Report for All Employees");
        System.out.println("------------------------------------------------------------");

        // Print column headings
        System.out.printf("%-10s %-15s %-20s %-8s%n",
                "Emp No", "First Name", "Last Name", "Salary");

        // Print employee salary information
        for (Employee emp : employees) {

            // Skip null employee records
            if (emp == null) {
                continue;
            }

            System.out.printf("%-10d %-15s %-20s %-8d%n",
                    emp.emp_no,
                    emp.first_name,
                    emp.last_name,
                    emp.salary);
        }
    }
    /**
     * Retrieves a department from the database using its name.
     *
     * @param connection Active MySQL database connection.
     * @param deptName Name of the department to retrieve.
     * @return Department object, or null if not found.
     */
    public static Department getDepartment(Connection connection, String deptName) {

        String sql = """
            SELECT dept_no, dept_name
            FROM departments
            WHERE dept_name = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, deptName);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Department department = new Department();

                    department.dept_no = result.getString("dept_no");
                    department.dept_name = result.getString("dept_name");

// Retrieve the current department manager
                    String managerSql = """
        SELECT
            e.emp_no,
            e.first_name,
            e.last_name
        FROM dept_manager dm
        JOIN employees e
            ON dm.emp_no = e.emp_no
        WHERE dm.dept_no = ?
          AND dm.to_date = '9999-01-01'
        """;

                    try (PreparedStatement managerStatement =
                                 connection.prepareStatement(managerSql)) {

                        managerStatement.setString(1, department.dept_no);

                        try (ResultSet managerResult = managerStatement.executeQuery()) {

                            if (managerResult.next()) {

                                Employee manager = new Employee();

                                manager.emp_no = managerResult.getInt("emp_no");
                                manager.first_name = managerResult.getString("first_name");
                                manager.last_name = managerResult.getString("last_name");

                                department.manager = manager;
                            }
                        }
                    }

                    return department;
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving department: " + e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves employees and their current salaries
     * for a specified department.
     *
     * @param connection Active MySQL database connection.
     * @param dept Department to retrieve salaries for.
     * @return List of employees and their salaries.
     */
    public static ArrayList<Employee> getSalariesByDepartment(
            Connection connection, Department dept) {

        ArrayList<Employee> employees = new ArrayList<>();

        if (dept == null) {
            System.err.println("Department cannot be null.");
            return employees;
        }

        String sql = """
            SELECT
                e.emp_no,
                e.first_name,
                e.last_name,
                s.salary
            FROM employees e
            JOIN salaries s
                ON e.emp_no = s.emp_no
            JOIN dept_emp de
                ON e.emp_no = de.emp_no
            WHERE de.dept_no = ?
              AND s.to_date = '9999-01-01'
              AND de.to_date = '9999-01-01'
            ORDER BY e.emp_no ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dept.dept_no);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Employee employee = new Employee();

                    employee.emp_no = result.getInt("emp_no");
                    employee.first_name = result.getString("first_name");
                    employee.last_name = result.getString("last_name");
                    employee.salary = result.getInt("salary");

                    employee.dept = dept;

                    employees.add(employee);
                }
            }

        } catch (Exception e) {
            System.err.println(
                    "Error retrieving salaries by department: " + e.getMessage());
            return null;
        }

        return employees;
    }
    /**
     * Retrieves an employee using their first and last names.
     *
     * @param connection Active MySQL database connection.
     * @param firstName Employee's first name.
     * @param lastName Employee's last name.
     * @return Matching employee, or null if not found.
     */
    public static Employee getEmployee(
            Connection connection,
            String firstName,
            String lastName) {

        String sql = """
            SELECT emp_no
            FROM employees
            WHERE first_name = ?
              AND last_name = ?
            ORDER BY emp_no ASC
            LIMIT 1
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int employeeNumber = result.getInt("emp_no");

                    // Reuse the existing employee retrieval method
                    return getEmployee(connection, employeeNumber);
                }
            }

        } catch (Exception e) {
            System.err.println(
                    "Error retrieving employee by name: " + e.getMessage());
        }

        return null;
    }
}

