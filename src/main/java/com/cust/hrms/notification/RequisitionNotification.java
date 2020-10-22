package com.cust.hrms.notification;

public class RequisitionNotification {
	private String message;
	
	public String getDraftedRequisitionMessage(boolean result) {
		if(result) {
			message = "Requisition has been drafted......";
		}
		else {
			message = "Enable to draft requisition.......";
		}
		return message;
	}
	
	public String getDeclinedRequisitionMessage(boolean result) {
		if(result) {
			message = "Requisition has been declined....";
		}
		else {
			message = "Enable to decline requisition....";
		}
		return message;
	}
	
	public String getApprovedRequisitionMessage(boolean result) {
		if(result) {
			message = "Requisition has been approved....";
		}
		else {
			message = "Enable to approve requisition....";
		}
		return message;
	}
	
	public String getClosedRequisitionMessage(boolean result) {
		if(result) {
			message = "Requisition has been closed.....";
		}
		else {
			message = "Enable to close requisition.....";
		}
		return message;
	}
	
	public String getSentToSupervisorForAuthorizationMessage(boolean result) {
		if(result) {
			message = "Requisition has been sent to supervisor for authorization....";
		}
		else {
			message = "Enable to send requisition to supervisor for authorization....";
		}
		return message;
	}
	
	public String getSentToRecipientForApprovalMessage(boolean result) {
		if(result) {
			message = "Requisition has been sent to recipient for approval......";
		}
		else {
			message = "Enable to send requisition to recipient for approval.....";
		}
		return message;
	}
	
	public String getDeletedRequisitionMessage(boolean result) {
		if(result) {
			message = "Requisition has been deleted.....";
		}
		else {
			message = "Enable to delete requisition....";
		}
		return message;
	}
}
