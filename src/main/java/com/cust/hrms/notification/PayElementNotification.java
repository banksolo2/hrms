package com.cust.hrms.notification;

public class PayElementNotification {
	private String message;
	
	public String getCreatePayElementMessage(boolean result) {
		if(result) {
			message = "Pay element has been created.....";
		}
		else {
			message = "Enable to create pay element.....";
		}
		return message;
	}
	
	public String getUpdatePayElementMessage(boolean result) {
		if(result) {
			message = "Pay element has been updated.....";
		}
		else {
			message = "Enable to update pay element.....";
		}
		return message;
	}
	
	public String getDeletePayElementMessage(boolean result) {
		if(result) {
			message = "Pay element has been deleted.....";
		}
		else {
			message = "Enable to delete pay element.....";
		}
		return message;
	}
	
	public String getNameAlreadyExistErrorMessage() {
		return "Pay element name already exist......";
	}
}
