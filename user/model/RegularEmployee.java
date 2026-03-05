package com.seveneleven.employeepayrollapp.user.model;


import com.seveneleven.employeepayrollapp.user.utilities.PasswordUtil;

/**
 * ------------- Regular Employee -------------
 * 
 * Represents a regular employee user.
 * 
 * Inheritance:
 * - RegularEmployee IS-A User
 * 
 * This class provides its own implementation
 * of the authenticate() method.
 */
public class RegularEmployee extends User{
	
	/**
	 * Constructor to initialize a regular employees.
	 * 
	 * @param username	The username of the employee
	 * @param password	The password of the employee
	 */
	public RegularEmployee(String username, String password) {
		super(username, password, "EMPLOYEE");
	}
	
	/**
	 * A method to authenticate a user against given credentials
	 * 
	 * @param username	The username of the employee
	 * @param password	The password of the employee
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
//		System.out.println("role: EMPLOYEE");
//		System.out.println("this.getRole(): " + this.getRole());
//		System.out.println("Equality[Role]: " + this.getRole().toUpperCase().equals("EMPLOYEE"));
		if(username.equals(username) && hashed.equals(this.passwordHash) && this.getRole().toUpperCase().equals("EMPLOYEE")) {
			return true;
		}
		
		return false;
	}
	
}
