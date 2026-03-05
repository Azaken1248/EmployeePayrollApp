
package com.seveneleven.employeepayrollapp.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.seveneleven.employeepayrollapp.payments.components.SalaryComponents;
import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;
import com.seveneleven.employeepayrollapp.payments.services.FileService;
import com.seveneleven.employeepayrollapp.payments.services.PayrollService;
import com.seveneleven.employeepayrollapp.payments.verification.DownloadToken;
import com.seveneleven.employeepayrollapp.user.auth.AuthenticationService;
import com.seveneleven.employeepayrollapp.user.dashboard.Dashboard;
import com.seveneleven.employeepayrollapp.user.dashboard.factory.DashboardFactory;
import com.seveneleven.employeepayrollapp.user.model.Employee;
import com.seveneleven.employeepayrollapp.user.model.UserAccount;
import com.seveneleven.employeepayrollapp.user.session.Session;
import com.seveneleven.employeepayrollapp.user.utilities.PasswordUtil;
import com.seveneleven.employeepayrollapp.user.validation.ValidationException;
import com.seveneleven.employeepayrollapp.user.validation.Validator;

/**
 * ------------- MAIN APP -------------
 * Main runner class for Use Case 5
 *
 * Role of main():
 * - Collect user input
 * - Prepare data
 * - Request appropriate dashboard
 * - Display dashboard
 * 
 * @author Developer
 * @version 5.0
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
	 * * @param targetUsername The username of the logged-in user
	 * @return The corresponding Employee object
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
	 * Method to persist simplified real data for the dashboard
	 * * @param empId  The ID of the employee
	 * @param month  The month of the payslip
	 * @param netPay The net pay amount
	 */
	private static void savePayslipForDashboard(String empId, String month, double netPay) {
		final String BASE_DIR = "./src/com/seveneleven/employeepayrollapp/user/storage/";
		try (FileWriter fw = new FileWriter(BASE_DIR + "payslips_data.txt", true);
			 BufferedWriter bw = new BufferedWriter(fw)) {
			
			bw.write(empId + "," + month + "," + netPay);
			bw.newLine();
			
		} catch (IOException e) {
			System.out.println("Warning: Could not save payslip for dashboard.");
		}
	}

	/**
	 * Method to load real generated payslips from persistent storage based on role
	 * * @param currentEmployee The currently logged-in employee
	 * @return An ArrayList of Payslip objects
	 */
	private static ArrayList<Payslip> loadRealPayslips(Employee currentEmployee) {
		ArrayList<Payslip> list = new ArrayList<>();
		final String BASE_DIR = "./src/com/seveneleven/employeepayrollapp/user/storage/";
		File file = new File(BASE_DIR + "payslips_data.txt");

		if (!file.exists()) return list; 

		boolean isManager = currentEmployee.getRole().equalsIgnoreCase("MANAGER");
		String currentEmpId = currentEmployee.getEmpId();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 3) {
					String recordEmpId = parts[0];
					String month = parts[1];
					double netPay = Double.parseDouble(parts[2]);

					if (isManager || recordEmpId.equals(currentEmpId)) {
						SalaryComponents sc = new SalaryComponents(0,0,0,0);
						sc.netPay = netPay;
						
						Employee recordEmp = new Employee(recordEmpId, "EMPLOYEE", "Unknown", "", "", null);
						list.add(new Payslip(recordEmp, sc, month));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading dashboard data: " + e.getMessage());
		}
		return list;
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
	 * * @param session         The active session
	 * @param currentEmployee The currently logged-in employee
	 */
	private static void loggedInMenu(Session session, Employee currentEmployee) {
		boolean inSession = true;
		
		ArrayList<Payslip> payslips = loadRealPayslips(currentEmployee);
		Dashboard dashboard = DashboardFactory.getDashboard(currentEmployee.getRole());
		
		if (dashboard != null) {
			dashboard.display(payslips, currentEmployee);
		}
		
		while(inSession) {
			if(session.isExpired()) {
				System.out.println("\nSession expired! Please log in again.");
				return; 
			}
			
			System.out.println("\n=== Actions ===");
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
	 * * @param currentEmployee The currently logged-in employee
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
			Payslip originalPayslip = payrollService.generatePayslip(currentEmployee, month, basic, hra, da, allowances);
			
			System.out.println(originalPayslip.toString());
			
			savePayslipForDashboard(currentEmployee.getEmpId(), month, originalPayslip.getNetPay());
			
			System.out.print("Would you like to print/download this payslip? (Y/N): ");
			if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
				
				System.out.println("Original Payslip: \nPAYSLIP");
				System.out.println(originalPayslip.toString());
				handlePayslipDownload(originalPayslip);
			}
			
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid number format! Please enter valid numeric salary components.");
		}
	}
	
	/**
	 * Method to handle payslip print/download flow
	 * * @param original The original Payslip object
	 */
	private static void handlePayslipDownload(Payslip original) {
		System.out.println("\n=== USE CASE 4: PAYSLIP PRINT / DOWNLOAD ===");
		
		try {
			Payslip cloned = (Payslip) original.clone();
			
			System.out.println("Verified: Download copy is equal to original");
			System.out.println("Original hashcode : " + original.hashCode());
			System.out.println("Cloned   hashcode : " + cloned.hashCode());
			
			DownloadToken token = new DownloadToken();
			if (token.isExpired()) {
				System.out.println("Download link has expired!");
				return;
			}
			
			FileService fileService = new FileService();
			String textFile = fileService.savePayslipAsText(cloned);
			String pdfFile = fileService.savePayslipAsPdf(cloned);
			
			System.out.println("\nPayslip Download Successful.");
			System.out.println("Saved as text file: " + textFile);
			System.out.println("Saved as PDF file : " + pdfFile);
			
			System.out.println("\n--- Printed Payslip ---");
			System.out.println(cloned.toString());
			
		} catch (Exception e) {
			System.out.println("Error during payslip download: " + e.getMessage());
		}
	}
	
	/**
	 * Entry point for payslip generation.
	 * * @param args Command-Line arguments
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
