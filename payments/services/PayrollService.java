package com.seveneleven.employeepayrollapp.payments.services;

import com.seveneleven.employeepayrollapp.payments.components.SalaryComponents;
import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;
import com.seveneleven.employeepayrollapp.user.model.Employee;

/**
 * ------------- Payroll Service -------------
 * 
 * PayrollService contains salary calculation logic.
 * 
 * Why this class exists:
 * - Business rules should not be inside main()
 * - Keeps calculations reusable and isolated
 * 
 * This introduces the idea of a SERVICE class.
 */
public class PayrollService {
	
	/**
	 * Generates a payslip by:
	 * - Creating salary components
	 * - Applying calculation rules
	 * - Returning a completed Payslip object
	 * 
	 * @param employee	 The employee whose payroll is to be calculated
	 * @param month		 The month in which the salary is credited
	 * @param basic		 The base salary of the employee
	 * @param hra		 The HRA
	 * @param da		 The DA
	 * @param allowances The allowances of the employee
	 * @return
	 */
	public Payslip generatePayslip(Employee employee, String month, double basic, double hra, double da, double allowances) {
		SalaryComponents salaryComponents = new SalaryComponents(basic, hra, da, allowances);
		
		//---- Gross Salary Calculation ----
		double gross = basic + hra + da + allowances;
		
		//---- Deductions ----
		salaryComponents.pf = basic * 0.12; // Provident Fund (12%)
		salaryComponents.tax = gross * 0.10; // Income Tax (10%) - demo rule
		
		//---- Net Pay ----
		salaryComponents.netPay = gross - (salaryComponents.pf + salaryComponents.tax);
		
		return new Payslip(employee, salaryComponents, month);
	}
}
