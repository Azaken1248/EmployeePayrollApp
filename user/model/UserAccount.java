package com.seveneleven.employeepayrollapp.user.model;

/**
 * ------------- UserAccount Class -------------
 * 
 * Why this is a seperate class:
 * - Employee details and login details are different concerns
 * - Keeps responsibilites small and clear
 * 
 * This introduces the idea of COMPOSITION:
 * - An Employee HAS a UserAccount
 */
public class UserAccount {
	private String username;
	private String passwordHash;
	
	/**
	 * Instantiate a user account
	 * 
	 * @param email	The email address of the employee
	 * @param passwordHash	The password hash 
	 */
	public UserAccount(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Set the username for the account
	 * 
	 * @param username	The username of the account
	 * @throws	IllegalArgumentException on null or empty username
	 */
	public void setUserame(String username) throws IllegalArgumentException{
		if(username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		
		this.username = username;
	}
	
	/**
	 * Set the passwordHash for the account
	 * 
	 * @param passwordHash	The hash of the password of the account
	 * @throws IllegalArgumentException on null or empty password
	 */
	public void setPasswordHash(String passwordHash) throws IllegalArgumentException{
		if(passwordHash == null || passwordHash.isEmpty()) {
			throw new IllegalArgumentException("Password hash cannot be null or empty");
		}
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Get the username associated with the account
	 * 
	 * @return	The username associated with the account
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Get the password hash associated with the account
	 * 
	 * @return	The password hash of the password associated with the account
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
}
