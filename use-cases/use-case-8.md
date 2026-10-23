# USE CASE: 8 Delete an Employee Record

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to delete an employee's details* so that *the organisation complies with data retention requirements.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available. The employee exists in the database. The HR advisor is authorised to delete employee records, and the employee's information is eligible for deletion under the organisation's data retention policy.

### Success End Condition

The employee's details are successfully deleted from the HR database in accordance with the organisation's data retention requirements.

### Failed End Condition

The employee's details are not deleted, and the existing employee record remains unchanged.

### Primary Actor

HR Advisor.

### Trigger

An employee's information reaches the end of its required retention period, and HR receives a request to delete the record.

## MAIN SUCCESS SCENARIO

1. HR advisor receives a request to delete an employee's details.
2. HR advisor accesses the HR system.
3. HR advisor selects the option to delete an employee record.
4. HR advisor enters the employee's identification number.
5. The system searches the database for the employee.
6. The system retrieves and displays the employee's details.
7. The system verifies that the employee's information is eligible for deletion under the organisation's data retention policy.
8. The system requests confirmation before deleting the employee record.
9. HR advisor reviews the information and confirms the deletion.
10. The system deletes the employee's eligible records from the database.
11. The system confirms that the employee's information was successfully deleted.

## EXTENSIONS

5. **Employee record does not exist**:
    1. The system informs the HR advisor that no matching employee record was found.
    2. No deletion is performed.

7. **Employee record is not eligible for deletion**:
    1. The system informs the HR advisor that the employee's information must be retained.
    2. No deletion is performed.

9. **HR advisor cancels the deletion**:
    1. The system cancels the deletion process.
    2. The employee's information remains unchanged.

10. **Database operation fails**:
    1. The system displays an appropriate error message.
    2. The deletion is rolled back to prevent incomplete changes.

## SUB-VARIATIONS

1. Employee records may have different retention periods depending on organisational policies.
2. Some employee information may require anonymisation rather than permanent deletion.
3. Deletion may require additional approval or authorisation.

## SCHEDULE

**DUE DATE**: Release 1.0