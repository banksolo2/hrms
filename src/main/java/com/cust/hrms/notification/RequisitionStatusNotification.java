package com.cust.hrms.notification;

public class RequisitionStatusNotification {
	private String message;
	
	public String getCreateRequisitionStatusMessage(boolean result) {
		if(result) {
			message = "Requisition status has been created...";
		}
		else {
			message = "Enable to create requisition status...";
		}
		return message;
	}
	
	public String getUpdateRequisitionStatusMessage(boolean result) {
		if(result) {
			message = "Requisition status has been updated....";
		}
		else {
			message = "Enable to update requisition status....";
		}
		return message;
	}
	
	public String getDeleteRequisitionStatusMessage(boolean result) {
		if(result) {
			message = "Requisition status has been deleted....";
		}
		else {
			message = "Enable to delete requisition status....";
		}
		return message;
	}
	
	public String getNameAlreadyExistMessage() {
		return "Name already exist.....";
	}
}
