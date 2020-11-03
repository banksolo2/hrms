package com.cust.hrms.notification;

public class LeaveNotification {
	private String message;
	
	public String getDraftedLeaveMessage(boolean result) {
		if(result) {
			message = "Leave request has been drafted....";
		}
		else {
			message = "Enable to draft leave request.....";
		}
		return message;
	}
	
	public String getApprovedLeaveMessage(boolean result) {
		if(result) {
			message = "Leave request has been approved...";
		}
		else {
			message = "Enable to approve leave request....";
		}
		return message;
	}
	
	public String getApprovedLeaveRecalledBackMessage(boolean result) {
		if(result) {
			message = "Leave recall back has been approved...";
		}
		else {
			message = "Enable to approve leave recall back...";
		}
		return message;
	}
	
	public String getDeclinedLeaveRecallBackMessage(boolean result) {
		if(result) {
			message = "Leave recall back has been declined...";
		}
		else {
			message = "Enable to decline leave recall back...";
		}
		return message;
	}
	
	public String getDeclinedLeaveRequestMessage(boolean result) {
		if(result) {
			message = "Leave request has been declined...";
		}
		else {
			message = "Enable to decline leave request...";
		}
		return message;
	}
	
	public String getInitiateLeaveRecallBackMessage(boolean result) {
		if(result) {
			message = "Leave recall back has been initiated...";
		}
		else {
			message = "Enable initiate leave recall back....";
		}
		
		return message;
	}
	
	public String getSentLeaveBackForCorrectionMessage(boolean result) {
		if(result) {
			message = "Leave request has been sent back for correction...";
		}
		else {
			message = "Enable to send leave request back for correction...";
		}
		return message;
	}
	
	public String getSentLeaveToHrForApprovalMessage(boolean result) {
		if(result) {
			message = "Leave request has been sent to HR for approval....";
		}
		else {
			message = "Enable to send leave request to HR for approval....";
		}
		return message;
	}
	
	public String getSentLeaveToSupervisorForApprovalMessage(boolean result) {
		if(result) {
			message = "Leave request has been sent to the supervisor for approval...";
		}
		else {
			message = "Enable to sent leave request to the supervisor for approval...";
		}
		return message;
	}
	
	public String getLeaveRedZoneErrorMessage() {
		return "The leave request is in a red zone period....";
	}
	
	public String getEmployeeLeaveDaysValidErrorMessage() {
		return "You have exceeded the number for your leave days...";
	}
	
	public String getInvalidStartDateErrorMessage() {
		return "Please provide a valid start date...";
	}
	
	public String getInlineWithLeavePlanErrorMessage() {
		return "Leave request is not in line with leave plan...";
	}
	
}
