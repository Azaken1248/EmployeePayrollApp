package com.seveneleven.employeepayrollapp.user.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.seveneleven.employeepayrollapp.user.model.Manager;
import com.seveneleven.employeepayrollapp.user.model.RegularEmployee;
import com.seveneleven.employeepayrollapp.user.model.User;
import com.seveneleven.employeepayrollapp.user.session.Session;

/**
 * ------------- Authentication Service -------------
 * 
 * AuthenticationService handles login-related operations.
 * 
 * Why this class exists:
 * - main() should not contain authentication login
 * - Centralized login rules and limits
 */
public class AuthenticationService {

	/**
	 * Map stores users using username as a key.
	 * 
	 * This simulates a data store
	 */
	private Map<String, User> users;
	private int maxAttempts = 3;

	/**
	 * Constructor to initialize the Auth service
	 */
	public AuthenticationService() {
		users = loadData();
	}

	/**
	 * Returns the session on login
	 * 
	 * @return	The current session
	 */
	@SuppressWarnings("resource")
	public Session login() {
		int attempts = 1;
		
		Scanner sc = new Scanner(System.in);
		
		while(attempts <= maxAttempts) {
			System.out.println("Attempt Number: " + attempts);
			System.out.print("Enter username: ");
			String username = sc.nextLine();

			System.out.print("Enter password: ");
			String password = sc.nextLine();

			User attemptedUser = users.get(username);

			if(attemptedUser == null) {
				System.out.print("User not registered!!");
				attempts++;
				continue;
			}

			boolean auth = attemptedUser.authenticate(username, password);

			if(auth) {
				System.out.println("Login Successful!");
				System.out.println("Role: " + attemptedUser.getRole());
				
				showDashboard(attemptedUser.getRole());
				
				Session currentSession = new Session(username);
				
				System.out.println("Session active for user: " + username);
				
				return currentSession;
				
			}else {
				System.out.println("Invalid login credentials!! Please try again");
			}

			attempts++;
		}

		System.out.println("Number of attempts exceeded!!");

		return null;
	}
	
	/**
	 * Private function to show the user dashboard
	 * 
	 * @param role	The role of the user
	 */
	private void showDashboard(String role) {
		System.out.println("====== DASHBOARD ======");
		System.out.println(role.toLowerCase() + "Dashboard");
		System.out.println("View Payslip | Update Profile");
	}

	/**
	 * Private function to load data from persistant storage
	 * 
	 * @return	The map containing all the user data
	 */
	private Map<String, User> loadData(){
		final String BASE_DIR  = "./src/com/seveneleven/employeepayrollapp/user/storage/";
		Map<String, User> loaded = new HashMap<>();

		File base = new File(BASE_DIR);
		if(!base.exists()) { 
			base.mkdirs();
		}

		File userFile = new File(BASE_DIR + "employee_data.txt");

		try(BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
			String line;
			while((line = reader.readLine()) != null) {
				String parts[] = line.split(",");

				if(line.length() >= 7) {
					String role = parts[1];
					String username = parts[2];
					String passwordHash = parts[3];
					


					loaded.put(username, role.toUpperCase().equals("MANAGER") ? new Manager(username, passwordHash) : new RegularEmployee(username, passwordHash));
				}
			}

		}catch(IOException e) {
			System.out.println("Error loading employee data: " + e.getMessage());
		}

		return loaded;	
	}
}
