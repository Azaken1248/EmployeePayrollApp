package com.seveneleven.employeepayrollapp.user.model;

import com.seveneleven.employeepayrollapp.user.utilities.PasswordUtil;

/**
 * ------------- Manager -------------
 * 
 * Represents a manager user.
 * 
 * This class also extends user,
 * but is treated differently based on role.
 */
public class Manager extends User{
	
	/**
	 * Constructor to initialize a manager.
	 * 
	 * @param username	The username of the manager
	 * @param password	The password of the manager
	 */
	public Manager(String username, String passwordHash) {
		super(username, passwordHash, "MANAGER");
	}
	
	/**
	 * A method to authenticate a user against given credentials
	 * 
	 * @param username	The username of the manager
	 * @param password	The password of the manager
	 * @return	True on succesfull authentication else false
	 */
	@Override
	public boolean authenticate(String username, String password) throws IllegalArgumentException{
		if(username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		
		if(password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}
		
		String hashed = PasswordUtil.hash(password);
		
//		System.out.println("username: " + username);
//		System.out.println("this.username: " + this.username);
//		System.out.println("Equality[username]: " + username.equals(this.username));
//		System.out.println("hashed: " + hashed);
//		System.out.println("this.passwordHash: " + this.passwordHash);
//		System.out.println("Equality[hash]: " + hashed.equals(this.passwordHash));
//		System.out.println("role: MANAGER");
//		System.out.println("this.getRole(): " + this.getRole());
//		System.out.println("Equality[Role]: " + this.getRole().toUpperCase().equals("MANAGER"));
		
		System.out.println(username.equals(this.username) && hashed.equals(this.passwordHash) && this.getRole().toUpperCase().equals("MANAGER"));
		if(username == super.username && hashed == super.passwordHash && super.getRole().toUpperCase() == "MANAGER") {
			return true;
		}
		
		return false;
	}
	
}
