package com.cust.hrms.notification;

public class EmployeePayElementDeductionNotification {
	private String message;
	
	public String getCreateEmployeePayElementDeductionMessage(boolean result) {
		if(result) {
			message = "Employee pay element deduction has been created....";
		}
		else {
			message = "Enable to create employee pay element deduction.....";
		}
		return message;
	}
	
	public String getUpdateEmployeePayElementDeductionMessage(boolean result) {
		if(result) {
			message = "Employee pay element deduction has been updated......";
		}
		else {
			message = "Enable to update employee pay element deduction......";
		}
		return message;
	}
	
	public String getDeleteEmployeePayElementDeductionMessage(boolean result) {
		if(result) {
			message = "Employee pay element deduction has been deleted......";
		}
		else {
			message = "Enable to delete employee pay element deduction......";
		}
		return message;
	}
	
	public String getInvalidYearFormatErrorMessage() {
		return "Invalid year format.....";
	}
	
	public String getInvalidAmountFormatErrorMessage() {
		return "Invalid amount format....";
	}
}
