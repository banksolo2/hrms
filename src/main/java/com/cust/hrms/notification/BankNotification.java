package com.cust.hrms.notification;

public class BankNotification {
	private String message;
	
	public String getCreateBankMessage(boolean result) {
		if(result) {
			message = "Bank information has been saved....";
		}
		else {
			message = "Enable to save bank information.....";
		}
		return message;
	}
	
	public String getUpdateBankMessage(boolean result) {
		if(result) {
			message = "Bank information has been updated....";
		}
		else {
			message = "Enable to update bank information.....";
		}
		return message;
	}
	
	public String getDeleteBankMessage(boolean result) {
		if(result) {
			message = "Bank information has been deleted....";
		}
		else {
			message = "Enable to delete bank information....";
		}
		return message;
	}
	
	public String getBankNameAlreadyExist() {
		return "Bank name already exist....";
	}
}
