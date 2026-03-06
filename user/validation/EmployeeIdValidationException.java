package com.seveneleven.employeepayrollapp.user.validation;

/**
 * Exception to be thrown on invalid employee id
 */
@SuppressWarnings("serial")
public class EmployeeIdValidationException extends ValidationException{
	public EmployeeIdValidationException(String message) {
		super(message);
	}
}