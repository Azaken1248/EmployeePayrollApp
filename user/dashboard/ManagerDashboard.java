package com.seveneleven.employeepayrollapp.user.dashboard;

import java.util.ArrayList;

import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;
import com.seveneleven.employeepayrollapp.user.model.Employee;

/**
 * ------------- Manager Dashboard -------------
 * 
 * ManagerDashboard provides an aggregate value.
 * 
 * Focus:
 * - Overall earnings
 * - Summary-level information
 * 
 */
public class ManagerDashboard implements Dashboard {
    
	/**
     * Displays manager-specific dashboard.
     * 
     * Only aggregation logic is applied her.
     */
	@Override
    public void display(ArrayList<Payslip> payslips, Employee employee) {
        System.out.println("\n=== MANAGER DASHBOARD ===");
        System.out.println("Manager: " + employee.getName());
        System.out.println("Dashboard Type: " + this.getClass().getName());

        // Calculate total team earnings
        double total = 0;
        for (Payslip p : payslips) {
            total += p.getNetPay();
        }
        System.out.println("\nTeam Total YTD Earnings: " + total);
    }
}