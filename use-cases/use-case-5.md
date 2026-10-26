# USE CASE: 5 Add a New Employee

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to add a new employee's details* so that *I can ensure the new employee is paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available. The HR advisor is authorised to create employee records. The necessary employee information has been provided, and the database is accessible.

### Success End Condition

A new employee record is successfully created and stored in the HR database, making the employee's details available for payroll processing.

### Failed End Condition

No new employee record is created, and existing employee information remains unchanged.

### Primary Actor

HR Advisor.

### Trigger

A new employee joins the organisation, and HR receives a request to register the employee in the system.

## MAIN SUCCESS SCENARIO

1. HR advisor receives the details of a newly recruited employee.
2. HR advisor accesses the HR system.
3. HR advisor selects the option to add a new employee.
4. The system requests the employee's required information.
5. HR advisor enters the employee's personal and employment details.
6. The system validates the information provided.
7. The system checks whether an employee record already exists.
8. The system creates a new employee record in the database.
9. The system confirms that the employee was successfully added.
10. HR advisor verifies the employee's details are correctly recorded.

## EXTENSIONS

6. **Required employee information is missing or invalid**:
    1. The system identifies the missing or invalid information.
    2. The system informs the HR advisor.
    3. The HR advisor corrects the information and resubmits it.

7. **Employee record already exists**:
    1. The system informs the HR advisor that a matching employee record exists.
    2. No duplicate record is created.

8. **Database connection fails**:
    1. The system displays an appropriate error message.
    2. No employee record is created.

## SUB-VARIATIONS

1. Employee information may be entered manually or obtained from an approved HR onboarding process.
2. Additional employment information may be required depending on the employee's role or department.

## SCHEDULE

**DUE DATE**: Release 1.0