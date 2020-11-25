package com.cust.hrms.notification;

public class ElementStatusNotification {
	private String message;
	
	public String getCreateElementStatusMessage(boolean result) {
		if(result) {
			message = "Element status has been created...";
		}
		else {
			message = "Enable to create element status.....";
		}
		return message;
	}
	
	public String getUpdatedElementStatusMessage(boolean result) {
		if(result) {
			message = "Element status has been updated....";
		}
		else {
			message = "Enable to update element status....";
		}
		return message;
	}
	
	public String getDeletedElementStatusMessage(boolean result) {
		if(result) {
			message = "Element status has been deleted...";
		}
		else {
			message = "Enable to delete element status....";
		}
		return message;
	}
	
	public String getElementStatusAlreadyExistErrorMessage() {
		message = "Element status name already exist........";
		return message;
	}
}
