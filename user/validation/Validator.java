package com.seveneleven.employeepayrollapp.user.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ------------- Validator Class -------------
 * 
 * This class is ONLY responsible for checking input correctness
 * 
 * Why we seperate validation:
 * - Keeps main() clean and readbale
 * - Avoids repeating validation logic
 * 
 * Important idea:
 * - Validation logic does NOT belong to Employee
 * - Validation happens before objects are created
 */
public class Validator {
	
	/**
	 * Regex pattern for standard email address
	 */
	public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	
	/**
	 * Regex pattern for an Indian phone number which can have
	 * only 10 digits and must begin with 6,7,8 or 0.
	 * 
	 */
	public static final String PHONE_REGEX = "[6-9]\\d{9}$";
	
	/**
	 * Regex to validate an employee ID with the format
	 * EMP-XXXX where X is a digit
	 */
	public static final String EMP_REGEX = "^EMP-\\d{4}$";
	
	/**
	 * Regex pattern for a password with at least 8 
	 * characters, a capital letter, a small letter
	 * and a special character
	 */
	public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)\\S{8,}$";
	
	/**
	 * Checks weather an email address follows a valid format.
	 * 
	 * If the format is wrong:
	 *  - A ValidationException is thrown
	 *  - Program flow jumps to the catch block in main()
	 *  
	 * @param email The email to be validated
	 * @throws EmailValidationException The exception thrown on invalid email
	 */
	public static void validateEmail(String email) throws EmailValidationException{
		if(email == null || email.isEmpty()) {
			throw new EmailValidationException("Email cannot be empty!!");
		}
		
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		
		if(!matcher.matches()) {
			throw new EmailValidationException("Email address format invalid please use valid format: user@example.com");
		}
	}
	
	/**
	 * Validate Indian phone numbers.
	 * 
	 * Rule:
	 *  - Must start with 6, 7, 8, or 9
	 *  - Must have exactly 10 digits
	 *  
	 * @param phone The phone number to be validated
	 * @throws PhoneValidationException PhoneValidationException The exception thrown on invalid phone number
	 */
	public static void validatePhone(String phone) throws PhoneValidationException{
		if(phone == null || phone.isEmpty()) {
			throw new PhoneValidationException("Phone number cannot be empty or null");
		}
		
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		Matcher matcher = pattern.matcher(phone);
		
		if(!matcher.matches()) {
			throw new PhoneValidationException("The phone number is of invalid format please enter a valid phone number.");
		}
		
	}
	
	/**
	 * Validate strength of passwords.
	 * 
	 * Rule:
	 *  - Must have atleast 8 characters
	 *  - Must have one capital letter
	 *  - Must have one small letter
	 *  - Must have one special character
	 *  
	 * @param password The password to be validated
	 * @throws PasswordValidationException PasswordValidationException The exception thrown on invalid phone number
	 */
	public static void validatePassword(String password) throws PasswordValidationException{
		if(password == null || password.isEmpty()) {
			throw new PasswordValidationException("Password cannot be null or empty.");
		}
		
		Pattern pattern = Pattern.compile(PASSWORD_REGEX);
		Matcher matcher = pattern.matcher(password);
		
		if(!matcher.matches()) {
			throw new PasswordValidationException("The password entered is too weak");
		}
	}
	
	/**
	 * Validates Employee ID format.
	 * 
	 * Rule:
	 * - Must follow EMP-XXXX where X is a digit
	 * 
	 * @param empId	The ID to be validated
	 * @throws EmployeeIdValidationException EmployeeIdValidationException thrown on invalid email id.
	 */
	public static void validateEmpId(String empId) throws EmployeeIdValidationException {
		if(empId == null || empId.isEmpty()) {
			throw new EmployeeIdValidationException("Employee ID cannot be empty or null");
		}
		
		Pattern pattern = Pattern.compile(EMP_REGEX);
		Matcher matcher = pattern.matcher(empId);
		
		if(!matcher.matches()) {
			throw new EmployeeIdValidationException("The employee ID is of invalid format");
		}
	}
}
