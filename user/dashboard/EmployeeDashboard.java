package com.seveneleven.employeepayrollapp.user.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;
import com.seveneleven.employeepayrollapp.user.model.Employee;

/**
 * EmployeeDashboard provides a personal view of payslip data.
 * 
 * Focus:
 * - Recemt payslips
 * - year-to-date earnings
 */
public class EmployeeDashboard implements Dashboard {
    
	/**
	 * Displays employee-specific dsahboard
	 * 
	 * Steps performed:
	 * - Sort payslips
	 * - Display to entries
	 * - Calculate total earnings
	 */
	@Override
    public void display(ArrayList<Payslip> payslips, Employee employee) {
        System.out.println("\n=== EMPLOYEE DASHBOARD ===");
        System.out.println("Welcome, " + employee.getName());
        System.out.println("Dashboard Type: " + this.getClass().getName());

        // Sort payslips in descending order of net pay using a Comparator
        Collections.sort(payslips, new Comparator<Payslip>() {
            @Override
            public int compare(Payslip p1, Payslip p2) {
                return Double.compare(p2.getNetPay(), p1.getNetPay());
            }
        });

        // Display only top 3 payslips
        System.out.println("\nRecent Payslips (Top 3):");
        int count = 0;
        Iterator<Payslip> it = payslips.iterator();
        while (it.hasNext() && count < 3) {
            Payslip p = it.next();
            System.out.println(p.getMonth() + "\t: " + p.getNetPay());
            count++;
        }

        // Calculate Year-To-Date earnings
        double total = 0;
        Iterator<Payslip> it2 = payslips.iterator();
        while (it2.hasNext()) {
            total += it2.next().getNetPay();
        }
        System.out.println("\nYear-To-Date Earnings: " + total);
    }
}
