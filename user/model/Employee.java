package com.seveneleven.employeepayrollapp.user.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ------------- Employee Class -------------
 * 
 * This class represents the Employee entity.
 * 
 * Core OOP concept introduced here:
 * - Encapsulation
 * 
 * Data is kept private and controlled through the class
 */
public class Employee {
	private String empId;
	private String role;
	private String name;
	private String email;
	private String phone;

	private UserAccount account;

	/**
	 * Constructor is used to create a fully initialized Employee object.
	 * 
	 * Important idea:
	 * - Object creation happens only after validation succeeds
	 * 
	 * @param empId		The employee id of the user
	 * @param name		The name of the employee
	 * @param email		The email id of the employee
	 * @param phone		The phone number of the employee
	 * @param account	The account associated with the employee
	 */
	public Employee(String empId, String role, String name, String email, String phone, UserAccount account) {
		this.empId = empId;
		this.role = role;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.account = account;
	}

	/**
	 * Get the employee ID of the employee
	 * 
	 * @return	The employee ID of the employee
	 */
	public String getEmpId() {
		return empId;
	}
	
	/**
	 * Get the role of the employee
	 * 
	 * @return	The role of the employee
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Get the name of the employee
	 * 
	 * @return	The name of the employee
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the name of Employee
	 * 
	 * @param name	The name to be set
	 * @throws IllegalArgumentException on empty or null name
	 */
	public void setName(String name) throws IllegalArgumentException {
		if(name == null || name.isEmpty()) {
			new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}

	/**
	 * Method to get the email of the employee
	 * 
	 * @return	The email of the employee
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to set the email for the employee
	 * 
	 * @param email	The email to set
	 * @throws	IllegalArgumentException on empty or null email
	 */
	public void setEmail(String email) {
		if(email == null || email.isEmpty()) {
			new IllegalArgumentException("Email cannot be null or empty");
		}
		this.email = email;
	}

	/**
	 * Method to get the phone number of the employee
	 * 
	 * @return	The phone number of the employee
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Method to set the phone number of the employee
	 * 
	 * @param phone	The phone number to be set
	 * @throws IllegalArgumentException on empty or null phone number
	 */
	public void setPhone(String phone) {
		if(phone == null || phone.isEmpty()) {
			new IllegalArgumentException("Phone number cannot be null or empty");
		}
		this.phone = phone;
	}

	/**
	 * Method to get the user account of the employee
	 * 
	 * @return	The account of the employee
	 */
	public UserAccount getAccount() {
		return account;
	}

	/**
	 * Method to get the employee as a formatted string
	 * 
	 * @return	The string representation of the employee object
	 */
	public String toString() {
		return String.format("Employee ID\t: %s\nName\t: %s\nEmail\t: %s\nPhone\t: %s\nUsername\t: %s", empId, name, email, phone, account.getUsername());
	}

	/**
	 * Method to store the user into the file system once they are registered
	 * 
	 * @throws IOException	In case the file doesn't exist
	 */
	public void persist() throws IOException {
		final String BASE_DIR  = "./src/com/seveneleven/employeepayrollapp/user/storage/";

		File base = new File(BASE_DIR);
		if(!base.exists()) { 
			base.mkdirs();
		}

		File userFile = new File(BASE_DIR + "employee_data.txt");

		BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true));

		String data = String.format("%s,%s,%s,%s,%s,%s,%s", empId,
				role,
				account.getUsername(),
				account.getPasswordHash(),
				name,
				email,
				phone);

		writer.write(data);
		writer.newLine();
		writer.close();
		
		System.out.println("Data persisted in file: " + userFile.toString());
	}
}
