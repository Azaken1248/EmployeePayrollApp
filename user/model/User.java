package com.seveneleven.employeepayrollapp.user.model;

/**
 * ------------- Abstract User Class -------------
 * 
 * User represents a generic system user.
 * 
 * Key concept introduced:
 * - Abstract class
 * 
 * Why this class exists:
 * - Different users exist (Employee, Manager)
 * - They share common data and behaviour
 * - But authentication logic must be defined by each type
 */
public abstract class User {
	protected String username;
	protected String passwordHash;
	protected String role;
	
	/**
	 * Constructor initializes shared user data.
	 * 
	 * Password is immediately hashed.
	 * 
	 * @param username	The username of the user
	 * @param password The password of the user
	 * @param role	The role of the user
	 */
	public User(String username, String passwordHash, String role) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}
	
	/**
	 * Abstract method:
	 * - Forces subclasses to provide authentication logic
	 * - Enables runtime method selection
	 * 
	 * @param username	The username of the user
	 * @param password	The password of the user
	 */
	public abstract boolean authenticate(String username, String password);
	
	/**
	 * Method to get the role of the user.
	 * 
	 * @return	The role of the user
	 */
	public String getRole(){
		return role;
	}
	
	
}
