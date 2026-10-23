# USE CASE: 7 Update an Employee Record

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to update an employee's details* so that *the employee's information is kept up to date.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available. The employee exists in the database, and the HR advisor is authorised to update employee information.

### Success End Condition

The employee's details are successfully updated and saved in the HR database.

### Failed End Condition

The employee's details are not updated, and the existing employee record remains unchanged.

### Primary Actor

HR Advisor.

### Trigger

HR receives a request to update an employee's personal or employment information.

## MAIN SUCCESS SCENARIO

1. HR advisor receives a request to update an employee's details.
2. HR advisor accesses the HR system.
3. HR advisor selects the option to update an employee record.
4. HR advisor enters the employee's identification number.
5. The system searches the database for the employee.
6. The system retrieves and displays the employee's existing information.
7. HR advisor selects the information that needs to be updated.
8. HR advisor enters the new information.
9. The system validates the updated information.
10. The system saves the changes to the employee's record.
11. The system confirms that the employee's information was successfully updated.
12. HR advisor verifies that the changes are correct.

## EXTENSIONS

5. **Employee record does not exist**:
    1. The system informs the HR advisor that no matching employee record was found.
    2. No changes are made.

9. **Updated information is missing or invalid**:
    1. The system identifies the missing or invalid information.
    2. The system informs the HR advisor.
    3. The HR advisor corrects the information and resubmits it.

10. **Database connection fails**:
    1. The system displays an appropriate error message.
    2. No changes are saved.

## SUB-VARIATIONS

1. Employee information may include personal details, job title, department and salary.
2. Certain updates may require additional authorisation depending on the type of information being changed.

## SCHEDULE

**DUE DATE**: Release 1.0