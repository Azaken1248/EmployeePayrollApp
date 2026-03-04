package com.seveneleven.employeepayrollapp.user.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * Checks weather an email address follows a valid format.
	 * 
	 * If the format is wrong:
	 *  - A ValidationException is thrown
	 *  - Program flow jumps to the catch block in main()
	 *  
	 * @param email The email to be validated
	 * @throws ValidationException The exception thrown on invalid email
	 */
	public static void validateEmail(String email) throws ValidationException{
		if(email == null || email.isEmpty()) {
			throw new ValidationException("Email cannot be empty!!");
		}
		
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		
		if(!matcher.matches()) {
			throw new ValidationException("Email address format invalid please use valid format: user@example.com");
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
	 * @throws ValidationException ValidationException The exception thrown on invalid phone number
	 */
	public static void validatePhone(String phone) throws ValidationException{
		if(phone == null || phone.isEmpty()) {
			throw new ValidationException("Phone number cannot be empty or null");
		}
		
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		Matcher matcher = pattern.matcher(phone);
		
		if(!matcher.matches()) {
			throw new ValidationException("The phone number is of invalid format please enter a valid phone number.");
		}
		
	}
	
	/**
	 * Validates Employee ID format.
	 * 
	 * Rule:
	 * - Must follow EMP-XXXX where X is a digit
	 * 
	 * @param empId	The ID to be validated
	 * @throws ValidationException
	 */
	public static void validateEmpId(String empId) throws ValidationException {
		if(empId == null || empId.isEmpty()) {
			throw new ValidationException("Employee ID cannot be empty or null");
		}
		
		Pattern pattern = Pattern.compile(EMP_REGEX);
		Matcher matcher = pattern.matcher(empId);
		
		if(!matcher.matches()) {
			throw new ValidationException("The employee ID is of invalid format");
		}
	}
}
