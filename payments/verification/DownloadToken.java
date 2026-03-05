package com.seveneleven.employeepayrollapp.payments.verification;

/**
 * ------------- Download Token -------------
 * 
 * DownloadToken represnets time-bound access to a download.
 * 
 * Purpose:
 * - Prevent unlimited access to generated files
 * - Simulate real-world expiry logic
 * 
 * This introduces the idea of time-based validation.
 */
public class DownloadToken {
	private long createdTime;
	private long expiryMillis;

	/**
	 * Constructor to create a downloadToken
	 */
	public DownloadToken() {
		this.createdTime = System.currentTimeMillis();
		this.expiryMillis = 60 * 1000; // 1 minute validity
	}

	/**
	 * Method to check if the token is expired
	 * 
	 * @return True if it is expires, false otherwise
	 */
	public boolean isExpired() {
		long now = System.currentTimeMillis();
		return (now - createdTime) > expiryMillis;
	}
}