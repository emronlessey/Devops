# USE CASE: 6 View an Employee Record

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to view an employee's details* so that *the employee's promotion request can be supported.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available. The employee exists in the database, and the database contains the employee's current employment information.

### Success End Condition

The employee's details are successfully retrieved and displayed, allowing the HR advisor to review information relevant to the promotion request.

### Failed End Condition

The employee's details cannot be retrieved or displayed.

### Primary Actor

HR Advisor.

### Trigger

An employee's promotion request requires HR to review the employee's employment details.

## MAIN SUCCESS SCENARIO

1. HR advisor receives a request to review an employee's details.
2. HR advisor accesses the HR system.
3. HR advisor selects the option to view an employee record.
4. HR advisor enters the employee's identification number.
5. The system searches the database for the employee.
6. The system retrieves the employee's personal and employment information.
7. The system retrieves the employee's current salary, job title, department and manager.
8. The system displays the employee's details.
9. HR advisor reviews the information to support the promotion request.

## EXTENSIONS

5. **Employee record does not exist**:
    1. The system informs the HR advisor that no matching employee record was found.
    2. No employee details are displayed.

6. **Database connection fails**:
    1. The system displays an appropriate error message.
    2. The employee's details cannot be retrieved.

7. **Current employment information is unavailable**:
    1. The system displays the available employee information.
    2. The system indicates which employment details are unavailable.

## SUB-VARIATIONS

1. Employee records may be retrieved using an employee identification number or another approved search criterion.
2. The information displayed may vary depending on the employee's available employment records.

## SCHEDULE

**DUE DATE**: Release 1.0