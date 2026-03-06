# EmployeePayrollApp

A console-based Java application designed to manage employee payroll operations, demonstrating how core Object-Oriented Programming (OOP) principles solve complex business problems while maintaining code quality, scalability, and maintainability.

## [Use Case 1](https://github.com/Azaken1248/employeepayrollapp/tree/feature/UC1-EmployeePayrollApp)

To register a new employee with validated personal information and establish the foundational entities of the application.

#### key concept

(1) **Encapsulation** and **Composition** to keep data private and separate login details into a distinct `UserAccount` object.

```java
public class Employee {
	private String empId;
	private String role;
	private String name;
	private String email;
	private String phone;
	private UserAccount account;

	public Employee(String empId, String role, String name, String email, String phone, UserAccount account) {
		this.empId = empId;
		this.role = role;
		// ... remaining initialization
		this.account = account;
	}
}

```

(2) **File I/O** to persist employee data into a text file, demonstrating how objects can manage their own data storage.

## [Use Case 2](https://github.com/Azaken1248/employeepayrollapp/tree/feature/UC2-EmployeePayrollApp)

To securely authenticate users and grant role-based access to the system.

#### key concept

(1) **Inheritance** and **Polymorphism** where specific user roles (`Manager`, `RegularEmployee`) extend an abstract `User` class and override the authentication logic.

```java
public abstract class User {
	protected String username;
	protected String passwordHash;
	protected String role;
	
	public abstract boolean authenticate(String username, String password);
}

```

(2) **Session Management** to maintain a time-based state for logged-in users, restricting access if the session expires.

## [Use Case 3](https://github.com/Azaken1248/employeepayrollapp/tree/feature/UC3-EmployeePayrollApp)

To generate a detailed monthly payslip breakdown by separating calculation logic from data representation.

#### key concept

(1) Using a **Service class** (`PayrollService`) to centralize business rules for calculating deductions and net pay, avoiding bloated main methods.

```java
public class PayrollService {
	public Payslip generatePayslip(Employee employee, String month, double basic, double hra, double da, double allowances) {
		SalaryComponents salaryComponents = new SalaryComponents(basic, hra, da, allowances);
		// Deduction and Net Pay calculation logic...
		return new Payslip(employee, salaryComponents, month);
	}
}

```

(2) Demonstrating **Aggregation** (`Payslip` HAS-A `Employee`) and **Composition** (`Payslip` owns `SalaryComponents`) to combine independent and dependent entities.

## [Use Case 4](https://github.com/Azaken1248/employeepayrollapp/tree/feature/UC4-EmployeePayrollApp)

To safely print and download payslips by protecting existing data from accidental modification.

#### key concept

(1) Implementing **Cloneable** and making the class `final` to ensure **Immutability** when generating independent payslip copies.

```java
public final class Payslip implements Cloneable {
	@Override
	public Object clone() {
		SalaryComponents clonedComponents = new SalaryComponents(components.basicSalary, components.hra, components.da, components.allowances);
		// Deep copy logic for deductions and net pay...
		return new Payslip(this.employee, clonedComponents, this.month);
	}
}

```

(2) Overriding **equals()** and **hashCode()** to establish a strict contract for object equality based on their values rather than memory references.

## [Use Case 5](https://github.com/Azaken1248/employeepayrollapp/tree/feature/UC5-EmployeePayrollApp)

To display different dashboards based on the user role using dynamic data processing.

#### key concept

(1) Implementing the **Factory Pattern** and a common **Interface** (`Dashboard`) to centralize object creation and enable runtime behavior selection.

```java
public class DashboardFactory {
    public static Dashboard getDashboard(String role) {
        return switch (role.toUpperCase()) {
            case "EMPLOYEE" -> new EmployeeDashboard();
            case "MANAGER" -> new ManagerDashboard();
            default -> null;
        };
    }
}

```

(2) Using **Collections** and a **Comparator** to process, sort, and display top payslip entries efficiently.

## [Use Case 6](https://github.com/Azaken1248/employeepayrollapp/tree/feature/UC6-EmployeePayrollApp)

To validate user input before it enters the system, preventing crashes and data corruption.

#### key concept

(1) Creating an **Exception Hierarchy** with custom checked exceptions to handle specific validation failures gracefully.

```java
@SuppressWarnings("serial")
public class EmailValidationException extends ValidationException {
	public EmailValidationException(String message) {
		super(message);
	}
}

```

(2) Utilizing **Regular Expressions (RegEx)** to define and enforce complex pattern matching for emails, phone numbers, employee IDs, and passwords.

## Integration Pipeline

Each feature is developed on a separate branch.
