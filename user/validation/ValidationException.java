package com.seveneleven.employeepayrollapp.user.validation;

/**
 * ------------- Custom Exception -------------
 * 
 * This class represents a validation-related problem.
 * 
 * Why this exists:
 * - Instead of stopping the program abruptly,
 *   we clearly communicate what went wrong
 *   
 * For now, this of this as,
 * "A special error we throw when input is invalid"
 */

@SuppressWarnings("serial")
public class ValidationException extends Exception{
	public ValidationException(String message) {
		super(message);
	}
}
