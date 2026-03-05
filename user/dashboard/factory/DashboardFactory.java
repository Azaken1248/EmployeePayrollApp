package com.seveneleven.employeepayrollapp.user.dashboard.factory;

import com.seveneleven.employeepayrollapp.user.dashboard.Dashboard;
import com.seveneleven.employeepayrollapp.user.dashboard.EmployeeDashboard;
import com.seveneleven.employeepayrollapp.user.dashboard.ManagerDashboard;

/**
 * ------------- Dashboard Factory -------------
 * 
 * Dashboard factory is responsible for cleaning dashboards.
 * 
 * Key idea introduced:
 * - Factory pattern
 * 
 * Why this is needed:
 * - Object creation logic is centralized
 * - main() does not need to know concrete classes
 */
public class DashboardFactory {
	
	/**
	 * Method to create a user object based on role
	 * 
	 * @param role	The role of the user
	 * @return the dashboard to be created	
	 */
    public static Dashboard getDashboard(String role) {
        return switch (role.toUpperCase()) {
            case "EMPLOYEE" -> new EmployeeDashboard();
            case "MANAGER" -> new ManagerDashboard();
            default -> null;
        };
    }
}
