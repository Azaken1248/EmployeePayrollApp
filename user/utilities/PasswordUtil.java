package com.seveneleven.employeepayrollapp.user.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ------------- Password Hash Utility -------------
 * 
 * This utility class handles password hashing.
 * 
 * Why this exists:
 * - Passwords should not be sorted or compared directly
 * - We convert password into a secure representation
 * 
 * At this stage:
 * - Focus on the idea of hashing
 * - Do NOT worry about cryptography details
 */
public class PasswordUtil {
	
	/**
	 * Converts a plain-text password into a hashed value.
	 * 
	 *  Same input -> same hash
	 *  Original password cannot be derived back
	 *  
	 *  @param password	The password to be hashed
	 *  @throws RuntimeException if the instance of the hashing algorithm is not found
	 */
	public static String hash(String password) throws RuntimeException {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] hashBytes = digest.digest(password.getBytes());
			
			StringBuilder hexString = new StringBuilder();
			for(byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			
			return hexString.toString();
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algorithm for hashing not found");
		}
	}
}
