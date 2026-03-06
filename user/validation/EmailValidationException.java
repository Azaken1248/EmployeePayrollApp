package com.seveneleven.employeepayrollapp.user.validation;

/**
 * Exception to be thrown on invalid email
 */
@SuppressWarnings("serial")
public class EmailValidationException extends ValidationException{
	public EmailValidationException(String message) {
		super(message);
	}
}
