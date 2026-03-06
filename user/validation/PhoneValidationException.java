package com.seveneleven.employeepayrollapp.user.validation;

/**
 * Exception to be thrown on invalid phone number
 */
@SuppressWarnings("serial")
public class PhoneValidationException extends ValidationException{
	public PhoneValidationException(String message) {
		super(message);
	}
}