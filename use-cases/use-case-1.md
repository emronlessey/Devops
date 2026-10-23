# USE CASE: 1 Produce a Salary Report for All Employees

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to produce a report on the salary of all employees* so that *I can support financial reporting of the organisation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

The HR system is available, and the database contains current employee salary information.

### Success End Condition

A report containing the current salary information of all employees is available for HR to provide to finance.

### Failed End Condition

No salary report is produced.

### Primary Actor

HR Advisor.

### Trigger

A request for salary information is received from the Finance Department.

## MAIN SUCCESS SCENARIO

1. Finance requests salary information for all employees.
2. HR advisor accesses the HR system.
3. HR advisor selects the option to produce a salary report for all employees.
4. The system retrieves the current salary information for all employees.
5. The system generates the salary report.
6. HR advisor reviews the report.
7. HR advisor provides the report to Finance.

## EXTENSIONS

4. **No employee salary records are available**:
    1. The system informs the HR advisor that no salary records were found.
    2. No report is produced.

4. **Database connection fails**:
    1. The system displays an error message.
    2. The HR advisor is unable to generate the report.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0