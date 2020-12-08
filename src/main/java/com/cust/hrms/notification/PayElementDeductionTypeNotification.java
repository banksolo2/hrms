package com.cust.hrms.notification;

public class PayElementDeductionTypeNotification {
	private String message;
	
	public String getCreatePayElementDeductionTypeMessage(boolean result) {
		if(result) {
			message = "Pay element deduction type has been created.....";
		}
		else {
			message = "Enable to create pay element deduction type......";
		}
		return message;
	}
	
	public String getUpdatePayElementDeductionTypeMessage(boolean result) {
		if(result) {
			message = "Pay element deduction type has been updated...";
		}
		else {
			message = "Enable to update pay element deduction type.....";
		}
		return message;
	}
	
	public String getDeletePayElementDeductionTypeMessage(boolean result) {
		if(result) {
			message = "Pay element deduction type has been deleted......";
		}
		else {
			message = "Enable to delete pay element deduction type.......";
		}
		return message;
	}
	
	public String getNameAlreadyExistErrorMessage() {
		return "Name already exist......"; 	
	}
}
