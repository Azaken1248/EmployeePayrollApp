package com.seveneleven.employeepayrollapp.payments.payroll;

import com.seveneleven.employeepayrollapp.payments.components.SalaryComponents;
import com.seveneleven.employeepayrollapp.user.model.Employee;

/**
 * ------------- Payslip -------------
 * 
 * Payslip represents a monthly salary statement.
 * 
 * It combines:
 * - Employee details (aggregation)
 * - Salary details (composition)
 * 
 * Payslip acts as a READ-ONLY view once created.
 */
public class Payslip {
	private Employee employee; // Aggregation
	private SalaryComponents components; // Composition
	private String month;
	
	/**
	 * Constructor to create a payslip object
	 * 
	 * @param employee		The employee object
	 * @param components	The salary components
	 * @param month			The month in which the salary is credited
	 */
	public Payslip(Employee employee, SalaryComponents components, String month) {
		this.employee = employee;
		this.components = components;
		this.month = month;
	}
	
	/**
	 * Formats payslip information into a readable output.
	 * 
	 * This avoids printing logic in main() or service classes.
	 * 
	 * @return a formatted string of the payslip
	 */
	public String toString() {
		return "============PAYSLIP============\n"
				+ "Month		:" + month + "\n"
				+ "Employee ID	:" + employee.getEmpId() + "\n"
				+ "Employee Name:" + employee.getName() + "\n\n"
				+ "---- Earnings ----\n"
				+ "Basic Salary	:" + components.basicSalary + "\n"
				+ "Month		:" + components.hra + "\n"
				+ "Month		:" + components.da + "\n"
				+ "Month		:" + components.allowances + "\n\n"
				+ "---- Decutioncs ----\n"
				+ "PF			:" + components.pf + "\n"
				+ "Tax			:" + components.tax + "\n\n"
				+ "Net Pay		:" + components.netPay + "\n"
				+"===============================\n";
	}
}
