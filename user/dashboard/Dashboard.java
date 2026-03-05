package com.seveneleven.employeepayrollapp.user.dashboard;

import java.util.ArrayList;

import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;
import com.seveneleven.employeepayrollapp.user.model.Employee;

/**
 * ------------- Dashboard Interface -------------
 * 
 * Dashboard defines a common contract for all dashboards.
 * 
 * Key idea introduced:
 * - Interface
 * 
 * Why an interface:
 * - Different dashboards exist
 * - All dashboards must provide a display() method
 * - Caller should not depend on concrete imnplementations
 */
public interface Dashboard {
	
	/**
	 * Abstract method to display the slip information
	 * @param payslips	The list of playsips used
	 * @param employee	The employee whose slip is to be displayes
	 */
    void display(ArrayList<Payslip> payslips, Employee employee);
}
