package com.cust.hrms.notification;

public class RequisitionTypeNotification {
	private String message;
	
	public String createRequisitionTypeMessage(boolean result) {
		if(result) {
			message = "Requisition type has been created.....";
		}
		else {
			message = "Enable to create requisition type......";
		}
		return message;
	}
	
	public String updateRequisitionTypeMessage(boolean result) {
		if(result) {
			message = "Requisition type has been updated....";
		}
		else {
			message = "Enable to update requisition type....";
		}
		return message;
	}
	
	public String deleteRequisitionTypeMessage(boolean result) {
		if(result) {
			message = "Requisition type has been deleted.......";
		}
		else {
			message = "Enable to delete requisition type.......";
		}
		return message;
	}
	public String getNameAlreadyExistMessage() {
		return "Name already exist.......";
	}
}
