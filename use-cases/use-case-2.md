# USE CASE: 2 Produce a Salary Report by Department

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to produce a report on the salary of employees in a department* so that *I can support financial reporting of the organisation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available. The department is known, and the database contains current employee salary and department information.

### Success End Condition

A report containing the current salary information of employees in the selected department is available for HR to provide to Finance.

### Failed End Condition

No salary report is produced.

### Primary Actor

HR Advisor.

### Trigger

A request for salary information for a specific department is received from Finance.

## MAIN SUCCESS SCENARIO

1. Finance requests salary information for a specific department.
2. HR advisor accesses the HR system.
3. HR advisor selects the option to produce a salary report by department.
4. HR advisor enters or selects the required department.
5. The system retrieves the current salary information for employees in the selected department.
6. The system generates the salary report.
7. HR advisor reviews the report.
8. HR advisor provides the report to Finance.

## EXTENSIONS

4. **Department does not exist**:
    1. The system informs the HR advisor that the selected department does not exist.
    2. No report is produced.

5. **No employees are assigned to the department**:
    1. The system informs the HR advisor that no employee records were found.
    2. No report is produced.

5. **Database connection fails**:
    1. The system displays an error message.
    2. The HR advisor is unable to generate the report.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0