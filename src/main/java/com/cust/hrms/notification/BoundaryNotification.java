package com.cust.hrms.notification;

public class BoundaryNotification {
	private String message;
	
	public String createBoundaryMessage(boolean result) {
		if(result) {
			message = "Pay boundary has been created....";
		}
		else {
			message = "Enable to create pay boundary....";
		}
		return message;
	}
	
	public String updateBoundaryMessage(boolean result) {
		if(result) {
			message = "Pay boundary has been updated....";
		}
		else {
			message = "Enable to update pay boundary....";
		}
		return message;
	}
	
	public String deleteBoundaryMessage(boolean result) {
		if(result) {
			message = "Pay boundary has been deleted...";
		}
		else {
			message = "Enable to delete pay boundary...";
		}
		return message;
	}
	
	public String getDefaultGreaterThanHighestAndLessThanLowerAmountErrorMessage() {
		message = "Default amount can't be greater than highest amount or less than the lowest amount....";
		return message;
	}
	
	public String getAmountFormatErrorMessage(String value) {
		message = "Invalid amount format for "+value+"....";
		return message;
	}
	
	public String getLowestGreaterThanHighestAmountErrorMessage() {
		return "Lowest amount can't be greater than highest amount...";
	}
	
	public String  getBoundaryAlreadyExistErrorMessage() {
		return "Boundary already exist....";
	}
}
