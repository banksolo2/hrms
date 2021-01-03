package com.cust.hrms.notification;

public class PayrollRoleNameNotification {
	private String message;
	
	public String getCreatePayrollRoleNameMessage(boolean result) {
		if(result) {
			message = "Payroll role name has been created......";
		}
		else {
			message = "Enable to create payroll role name.......";
		}
		return message;
	}
	
	public String getUpdatePayrollRoleNameMessage(boolean result) {
		if(result) {
			message = "Payroll role name has been updated.....";
		}
		else {
			message = "Enable to update payroll role name......";
		}
		return message;
	}
	
	public String getDeletePayrollRoleNameMessage(boolean result) {
		if(result) {
			message = "Payroll role name has been deleted......";
		}
		else {
			message = "Enable to delete payroll role name.......";
		}
		return message;
	}
	
	public String getNameAlreadyExistErrorMessage() {
		return "Name already exist......";
	}
}
