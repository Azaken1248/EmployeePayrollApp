package com.seveneleven.employeepayrollapp.payments.services;

import java.io.FileWriter;
import java.io.IOException;

import com.seveneleven.employeepayrollapp.payments.payroll.Payslip;

/**
 * ------------- File Service -------------
 * 
 * FileService handles saving payslip data to files.
 * 
 * Why this class exists:
 * - File operations should not be inside Payslip
 * - Seperates persistance from data representation
 */
public class FileService {
	private static final String DIR = "./src/com/seveneleven/employeepayrollapp/user/storage/";
	
	/**
	 * Saves the payslip as a text file.
	 * 
	 * A unique filename is generated using timestamp
	 * to avoid overwritting existing files.
	 * 
	 * @param payslip		The payslip to be printed
	 * @return				The name of the save file
	 * @throws IOException	Exception to be thrown incase of IO Error
	 */
	public String savePayslipAsText(Payslip payslip) throws IOException {
		String fileName = "Payslip_" + payslip.getEmployee().getEmpId() + "_" + System.currentTimeMillis() + ".txt";
		try(FileWriter fw = new FileWriter(DIR + fileName)) {
			fw.write(payslip.toString());
		}
		return fileName;
	}
	
	/**
	 * Saves the payslip as a PDF file.
	 * 
	 * Note:
	 * - This is a simplified demo
	 * - Content is plain text with a .pdf extension
	 * 
	 * @param payslip		The payslip to be printed
	 * @return				The name of the save file
	 * @throws IOException	Exception to be thrown incase of IO Error
	 */
	public String savePayslipAsPdf(Payslip payslip) throws IOException {
		String fileName = "Payslip_" + payslip.getEmployee().getEmpId() + "_" + System.currentTimeMillis() + ".pdf";
		try(FileWriter fw = new FileWriter(DIR + fileName)) {
			fw.write(payslip.toString());
		}
		return fileName;
	}
}
