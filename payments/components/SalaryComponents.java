package com.seveneleven.employeepayrollapp.payments.components;

/**
 * ------------- Salary Components (Composition) -------------
 * 
 * SalaryComponents groups all salary-related values.
 * 
 * Why this class exists:
 * - Salary details belong together
 * - Keeps Payslip clean and readable
 * 
 * This introduces COMPOSITION:
 * - Payslip owns SalaryComponents
 * - SalaryComponents has no meaning without Payslip
 */
public class SalaryComponents {
	public double basicSalary;
	public double hra;
	public double da;
	public double allowances;
	public double pf;
	public double tax;
	public double netPay;

	/**
	 * Constructor initializes only earnings.
	 * 
	 * Deductions and net pay are calculated later.
	 * 
	 * @param basicSalary	The base salary of the employee
	 * @param hra			The HRA of the employee
	 * @param da			The DA of the employee
	 * @param allowances	The allowances of the employee
	 */
	public SalaryComponents(double basicSalary, double hra, double da, double allowances) {
		this.basicSalary = basicSalary;
		this.hra = hra;
		this.da = da;
		this.allowances = allowances;
	} 
}
