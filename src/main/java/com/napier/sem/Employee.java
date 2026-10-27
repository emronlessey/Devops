package com.napier.sem;

/**
 * Represents an employee within the organisation.
 */
public class Employee {

    public int emp_no;
    public String first_name;
    public String last_name;
    public String gender;
    public String birth_date;
    public String hire_date;

    // Additional employee information
    public int salary;
    public String title;

    /**
     * Department to which the employee belongs.
     */
    public Department dept;

    /**
     * Employee responsible for managing this employee.
     */
    public Employee manager;
}