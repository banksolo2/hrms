package com.cust.hrms.notification;

public class EmployeeStatusNotification {
	private String message;
	
	public String createEmployeeStatusMessage(boolean result) {
		if(result) {
			message = "Employee status has been created.....";
		}
		else {
			message = "Enable to create employee status......";
		}
		return message;
	}
	
	public String updateEmployeeStatusMessage(boolean result) {
		if(result) {
			message = "Employee status has been updated....";
		}
		else {
			message = "Enable to update employee status.....";
		}
		return message;
	}
	
	public String deleteEmployeeStatusMessage(boolean result) {
		if(result) {
			message = "Employee status has been deleted......";
		}
		else {
			message = "Enable to delete employee status......";
		}
		return message;
	}
	
	public String getNameAlreadyExistErrorMessage() {
		return "Name already exist..........";
	}

}
