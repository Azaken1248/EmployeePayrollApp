package com.seveneleven.employeepayrollapp.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;
import com.seveneleven.employeepayrollapp.payments.services.PayrollService;
import com.seveneleven.employeepayrollapp.user.auth.AuthenticationService;
import com.seveneleven.employeepayrollapp.user.model.Employee;
import com.seveneleven.employeepayrollapp.user.model.UserAccount;
import com.seveneleven.employeepayrollapp.user.session.Session;
import com.seveneleven.employeepayrollapp.user.utilities.PasswordUtil;
import com.seveneleven.employeepayrollapp.user.validation.ValidationException;
import com.seveneleven.employeepayrollapp.user.validation.Validator;

/**
 * ------------- MAIN APP -------------
 * Main runner class for Use Case 3
 *
 * Role of main():
 * - Collect input
 * - Create required objects
 * - Delegate calculations
 * - Display final output
 * 
 * main() does NOT perform calculations itself.
 * 
 * @author Developer
 * @version 3.0
 */
public class EmployeePayrollApp {
	private static final Scanner sc = new Scanner(System.in);
	
	/**
	 * Method to handle registration flow
	 */
	public static void handleRegistrationFlow() {
		System.out.println("\n=== USE CASE 1: EMPLOYEE REGISTRATION ===");
		
		try {
			System.out.print("Enter Employee ID (EMP-XXXX): ");
			String empId = sc.nextLine();
			
			System.out.print("Enter Role(MANAGER/EMPLOYEE): ");
			String role = sc.nextLine().toUpperCase();
			
			System.out.print("Enter Name: ");
			String name = sc.nextLine();
			
			System.out.print("Enter Email: ");
			String email = sc.nextLine();
			
			System.out.print("Enter Phone (10 digits starting 6-9): ");
			String phone = sc.nextLine();
			
			System.out.print("Create Username: ");
			String username = sc.nextLine();
			
			System.out.print("Enter Password: ");
			String password = sc.nextLine();
			
			Validator.validateEmpId(empId);
			Validator.validateEmail(email);
			Validator.validatePhone(phone);
			
			Employee newEmployee = new Employee(empId, role, name, email, phone, new UserAccount(username, PasswordUtil.hash(password.trim())));
			
			System.out.println("-----------------------------------------------");
			System.out.println("Employee Registered Successfully:");
			System.out.println(newEmployee.toString());
			newEmployee.persist();
			System.out.println("-----------------------------------------------");
			
		} catch(ValidationException e) {
			System.out.println("\nValidation Failed: " + e.getMessage());
		} catch(IOException e) {
			System.out.println("\nError saving employee data!");
		}
	}
	
	/**
	 * Helper method to fetch full Employee details for the logged-in user
	 */
	private static Employee getEmployeeByUsername(String targetUsername) {
		final String BASE_DIR = "./src/com/seveneleven/employeepayrollapp/user/storage/";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(BASE_DIR + "employee_data.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 7 && parts[2].equals(targetUsername)) {
					String empId = parts[0];
					String role = parts[1];
					String passwordHash = parts[3];
					String name = parts[4];
					String email = parts[5];
					String phone = parts[6];
					
					UserAccount account = new UserAccount(targetUsername, passwordHash);
					return new Employee(empId, role, name, email, phone, account);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading employee profile: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Method to handle login flow
	 */
	public static void handleLoginFlow() {
		System.out.println("\n=== USE CASE 2: EMPLOYEE AUTHENTICATION & LOGIN ===");
		
		AuthenticationService authService = new AuthenticationService();
		Session session = authService.login();
		
		if(session != null) {
			System.out.println("Session is active and valid.");
			
			String username = session.toString().replace("Session active for user: ", "").trim();
			Employee currentEmployee = getEmployeeByUsername(username);
			
			if (currentEmployee != null) {
				loggedInMenu(session, currentEmployee);
			} else {
				System.out.println("Error: Could not retrieve employee profile data.");
			}
		}
	}

	/**
	 * Method to handle logged in menu
	 */
	private static void loggedInMenu(Session session, Employee currentEmployee) {
		boolean inSession = true;
		
		while(inSession) {
			if(session.isExpired()) {
				System.out.println("\nSession expired! Please log in again.");
				return; 
			}
			
			System.out.println("\n=== User Dashboard ===");
			System.out.println("Welcome, " + currentEmployee.getName());
			System.out.println("1. Generate Payslip");
			System.out.println("0. Logout");
			
			System.out.print("Enter Choice: ");
			String choice = sc.nextLine();
			
			switch(choice) {
				case "1" -> handlePayslipGeneration(currentEmployee);
				case "0" -> {
					System.out.println("Logging out...");
					inSession = false; 
				}
				default -> System.out.println("Invalid Choice!!");
			}
		}
	}

	/**
	 * Method to handle Payslip generation flow
	 */
	private static void handlePayslipGeneration(Employee currentEmployee) {
		System.out.println("\n=== USE CASE 3: PAYSLIP GENERATION ===");
		System.out.println("Generating Payslip for: " + currentEmployee.getName() + " (" + currentEmployee.getEmpId() + ")");
		
		System.out.print("Enter Month (e.g., January 2026): ");
		String month = sc.nextLine();
		
		try {
			System.out.print("Enter Basic Salary: ");
			double basic = Double.parseDouble(sc.nextLine());
			
			System.out.print("Enter HRA: ");
			double hra = Double.parseDouble(sc.nextLine());
			
			System.out.print("Enter DA: ");
			double da = Double.parseDouble(sc.nextLine());
			
			System.out.print("Enter Allowances: ");
			double allowances = Double.parseDouble(sc.nextLine());
			
			PayrollService payrollService = new PayrollService();
			Payslip payslip = payrollService.generatePayslip(currentEmployee, month, basic, hra, da, allowances);
			
			System.out.println(payslip.toString());
			
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid number format! Please enter valid numeric salary components.");
		}
	}
	
	/**
	 * Entry point for payslip generation.
	 * 
	 * Execution Flow:
	 * 1. Capture employee details
	 * 2. Capture salary components
	 * 3. Generate payslip via service
	 * 4. Display formatted payslip
	 * 
	 * @param args	Command-Line arguments
	 */
	public static void main(String[] args) {
		boolean inFlow = true;
		
		while(inFlow) {
			System.out.println("\n=== Menu ===");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("0. Exit");
			
			System.out.print("Enter Choice: ");
			String input = sc.nextLine();
			
			inFlow = switch(input) {
				case "1" -> {
					handleRegistrationFlow();
					yield true;
				}
				case "2" -> {
					handleLoginFlow();
					yield true; 
				}
				case "0" -> {
					System.out.println("Thank You!!");
					yield false;
				}
				default -> {
					System.out.println("Invalid Choice!!");
					yield true;
				}
			};
		}
	}
}