package com.seveneleven.employeepayrollapp.user.session;

/**
 * ------------- Session Management -------------
 * 
 * Session represents a logged-in user state.
 * 
 * Why this class exists:
 * - Login is not permanent
 * - Session has a lifetime
 * 
 * This indroduces the idea of time-based state.
 */
public class Session {
	private String username;
	private long loginTime;
	private long timeoutMillis;
	
	/**
	 * Constructor to initialize a session
	 * 
	 * @param username	The username of the user logged in
	 */
	public Session(String username) {
		this.username = username;
		this.loginTime = System.currentTimeMillis();
		this.timeoutMillis = 300000; // 5 min per session
	}
	
	/**
	 * Checks weather te session is still valid
	 * 
	 * @return True if the session is expired else false
	 */
	public boolean isExpired() {
		return (System.currentTimeMillis() - loginTime) > timeoutMillis;
	}
	
	/**
	 * Method to get the string representation of the session
	 * 
	 * @return The string representation of the session
	 */
	@Override
	public String toString() {
		return "Session active for user: " + username;
	}
}
