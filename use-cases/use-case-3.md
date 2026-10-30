# USE CASE: 3 Produce a Department Salary Report for Managers

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *department manager* I want *to produce a report on the salary of employees in my department* so that *I can support financial reporting for my department.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available. The department manager is authorised to access salary information for their department. The database contains current employee salary and department information.

### Success End Condition

A report containing the current salary information of employees in the manager's department is available for departmental financial reporting.

### Failed End Condition

No salary report is produced.

### Primary Actor

Department Manager.

### Trigger

The department manager requires salary information for departmental financial reporting.

## MAIN SUCCESS SCENARIO

1. Department manager accesses the HR system.
2. Department manager selects the option to produce a salary report for their department.
3. The system identifies the department associated with the manager.
4. The system verifies that the manager is authorised to access the department's salary information.
5. The system retrieves the current salary information for employees in the manager's department.
6. The system generates the department salary report.
7. Department manager reviews the report.
8. Department manager uses the report to support departmental financial reporting.

## EXTENSIONS

4. **Manager is not authorised to access the department's salary information**:
    1. The system denies access.
    2. The system displays an appropriate error message.
    3. No report is produced.

5. **No employee salary records are available for the department**:
    1. The system informs the department manager that no salary records were found.
    2. No report is produced.

5. **Database connection fails**:
    1. The system displays an error message.
    2. The department manager is unable to generate the report.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0