package com.seveneleven.employeepayrollapp.payments.payroll;

import com.seveneleven.employeepayrollapp.payments.components.SalaryComponents;
import com.seveneleven.employeepayrollapp.user.model.Employee;

/**
 * ------------- Immutable Payslip -------------
 * 
 * Payslip represents a finalized salary record.
 * 
 * Key idea introduced here:
 *  - Immutability
 * 
 * Once a payslip is generated:
 * - Its data should never change
 * - Any operation (print / download) must use a copy
 * 
 * Making the class final prevents inheritance-based modification.
 */
public final class Payslip implements Cloneable{
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
	 * Method to return the employee
	 * 
	 * @return	The current employee
	 */
	public Employee getEmployee() {
		return employee;
	}
	
	/**
	 * Method to get the salary components of the employee
	 * 
	 * @return	The salary components of the employee
	 */
	public SalaryComponents getComponents() {
		return components;
	}
	
	/**
	 * Method to get the month of the payslip
	 * 
	 * @return The month of the payslip
	 */
	public String getMonth() {
		return month;
	}
	
	
	/**
	 * Method to check if two slips are equal
	 * 
	 * @return	True if they are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Payslip payslip = (Payslip) o;
		return month.equals(payslip.month) && employee.getEmpId().equals(payslip.employee.getEmpId());
	}
	
	/**
	 * Method to clone a payslip
	 * 
	 * @return an Object
	 */
	@Override
	public Object clone() {
		SalaryComponents clonedComponents = new SalaryComponents(components.basicSalary, components.hra, components.da, components.allowances);
		clonedComponents.pf = components.pf;
		clonedComponents.tax = components.tax;
		clonedComponents.netPay = components.netPay;
		return new Payslip(this.employee, clonedComponents, this.month);
	}
	
	/**
	 * Get the hashCode of the payslip class
	 * 
	 * @return	The hashCode of the payslip class
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + employee.getEmpId().hashCode();
		result = 31 * result + month.hashCode();
		return result;
	}
	/**
	 * Formats payslip information into a readable output.
	 * 
	 * This avoids printing logic in main() or service classes.
	 * 
	 * @return a formatted string of the payslip
	 */
	@Override
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
