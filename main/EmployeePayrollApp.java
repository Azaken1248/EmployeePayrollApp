package com.seveneleven.employeepayrollapp.main;

import java.io.IOException;
import java.util.Scanner;

import com.seveneleven.employeepayrollapp.user.auth.AuthenticationService;
import com.seveneleven.employeepayrollapp.user.model.Employee;
import com.seveneleven.employeepayrollapp.user.model.UserAccount;
import com.seveneleven.employeepayrollapp.user.session.Session;
import com.seveneleven.employeepayrollapp.user.utilities.PasswordUtil;
import com.seveneleven.employeepayrollapp.user.validation.ValidationException;
import com.seveneleven.employeepayrollapp.user.validation.Validator;

/**
 * ------------- Main Class -------------
 * 
 * Entry point for Use Case 2
 * 
 * Execution Flow:
 * 1. Take input from user
 * 2. Validate input
 * 3. Create objects
 * 4. Persist data
 * 5. Display confirmation
 * 6. Trigger login
 * 7. Recieve session
 * 8. Validate session state
 * 
 * @author Developer
 * @version 2.0
 */
public class EmployeePayrollApp {
	private static final Scanner sc = new Scanner(System.in);
	
	/**
	 * Method to handle registration flow
	 */
	public static void handleRegistrationFlow() {
		System.out.println("=== USE CASE 1: EMPLOYEE REGISTRATION ===");
		
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
			
			
			
		}catch(ValidationException e) {
			System.out.println("\nValidation Failed: " + e.getMessage());
		}catch(IOException e) {
			System.out.println("\nError saving employee data!");
		}
	}
	
	/**
	 * Method to handle login flow
	 */
	public static void handleLoginFlow() {
		System.out.println("=== USE CASE 2: EMPLOYEE AUTHENTICATION & LOGIN ===");
		
		AuthenticationService authService = new AuthenticationService();
		Session session = authService.login();
		
		if(session != null) {
			System.out.println("Session is active and valid.");
		}
	}
	public static void main(String[] args) {
		
		boolean inFlow = true;
		
		while(inFlow) {
			System.out.println("=== Menu ===");
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
					yield false;
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
