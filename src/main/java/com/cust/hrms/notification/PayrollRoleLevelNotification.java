package com.cust.hrms.notification;

public class PayrollRoleLevelNotification {
	private String message;
	
	public String getCreatePayrollRoleLevelMessage(boolean result) {
		if(result) {
			message = "Payroll role level has been created...";
		}
		else {
			message = "Enable to created payroll role level.....";
		}
		return message;
	}
	
	public String getUpdatePayrollRoleLevelMessage(boolean result) {
		if(result) {
			message = "Payroll role level has been updated...";
		}
		else {
			message = "Enable to update payroll role level....";
		}
		return message;
	}
	
	public String getDeletePayrollRoleLevelMessage(boolean result) {
		if(result) {
			message = "Payroll role level has been deleted....";
		}
		else {
			message = "Enable to delete payroll role level.....";
		}
		return message;
	}
	
	public String getPayrollRoleLevelAlreadyExistErrorMessage() {
		return "Payroll role level already exist........";
	}
}
