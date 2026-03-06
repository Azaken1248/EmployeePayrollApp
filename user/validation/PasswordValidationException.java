package com.seveneleven.employeepayrollapp.user.validation;

/**
 * Exception to be thrown on invalid password
 */
@SuppressWarnings("serial")
public class PasswordValidationException extends ValidationException{
	public PasswordValidationException(String message) {
		super(message);
	}
}
