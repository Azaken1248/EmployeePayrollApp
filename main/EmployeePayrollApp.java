package com.seveneleven.employeepayrollapp.main;

import java.io.IOException;
import java.util.Scanner;

import com.seveneleven.employeepayrollapp.user.model.Employee;
import com.seveneleven.employeepayrollapp.user.model.UserAccount;
import com.seveneleven.employeepayrollapp.user.validation.ValidationException;
import com.seveneleven.employeepayrollapp.user.validation.Validator;

/**
 * ------------- Main Class -------------
 * 
 * Entry point for Use Case 1
 * 
 * Execution Flow:
 * 1. Take input from user
 * 2. Validate input
 * 3. Create objects
 * 4. Persist data
 * 5. Display confirmation
 * 
 * @author Developer
 * @version 1.0
 */
public class EmployeePayrollApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== USE CASE 1: EMPLOYEE REGISTRATION ===");
		
		try {
			System.out.print("Enter Employee ID (EMP-XXXX): ");
			String empId = sc.nextLine();
			
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
			
			Employee newEmployee = new Employee(empId, name, email, phone, new UserAccount(username, password));
			
			
			
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
		
		sc.close();
	}
}
