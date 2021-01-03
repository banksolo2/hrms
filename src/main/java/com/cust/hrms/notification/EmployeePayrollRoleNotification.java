package com.cust.hrms.notification;

public class EmployeePayrollRoleNotification {
	private String message;
	
	public String getCreateEmployeePayrollRoleMessage(boolean result) {
		if(result) {
			message = "Employee payroll role has been created....";
		}
		else {
			message = "Enable to create employee payroll role.....";
		}
		return message;
	}
	
	public String getUpdateEmployeePayrollRoleMessage(boolean result) {
		if(result) {
			message = "Employee payroll role has been updated......";
		}
		else {
			message = "Enable to update employee payroll role.....";
		}
		return message;
	}
	
	public String getDeleteEmployeePayrollRoleMessage(boolean result) {
		if(result) {
			message = "Employee payroll role has been deleted.....";
		}
		else {
			message = "Enable to delete employee payroll role.....";
		}
		return message;
	}
	
	public String getEmployeeAlreadyExistErrorMessage() {
		return "Employee already has a role........";
	}
}
