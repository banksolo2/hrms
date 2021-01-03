package com.cust.hrms.notification;

public class PayrollNotification {
	private String message;
	
	public String getCreatePayrollMessage(boolean result) {
		if(result) {
			message = "Payroll has been created.....";
		}
		else {
			message = "Enable to create payroll.....";
		}
		return message;
	}
	
	public String getUpdatePayrollMessage(boolean result) {
		if(result) {
			message = "Payroll has been updated.....";
		}
		else {
			message = "Enable to update payroll......";
		}
		return message;
	}
	
	public String getDeletePayrollMessage(boolean result) {
		if(result) {
			message = "Payroll has been deleted......";
		}
		else {
			message = "Enable to delete payroll......";
		}
		return message;
	}
	
	public String getPayrollAlreadyExistErrorMessage() {
		return "Payroll already exist............";
	}
	
	public String getInvalidYearErrorMessage() {
		return "Invalid year format.......";
	}
}
