package com.cust.hrms.dao;


public class NotificationMessageDao {
	
	public String getInitiateLeaveRecallBackMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave recall back has been initialized.....";
		}
		else {
			message = "Enable to initialize leave recall back......";
		}
		
		return message;
	}
	
	public String getLeaveSendToHrForApprovalMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave request has been sent to HR for approval...";
		}
		else {
			message = "Enable to send leave request to HR for approval...";
		}
		
		return message;
	}
	
	public String getLeaveApprovedMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave request has been approved....";
		}
		else {
			message = "Enable to approve leave request....";
		}
		return message;
	}
	
	public String getLeaveDeclinedMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave request has been declined....";
		}
		else {
			message = "Enable to decline leave request....";
		}
		return message;
	}
	
	public String getLeaveSentBackForCorrectionMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave request has been sent back for correction...";
		}
		else {
			message = "Enable to send leave request back for correction...";
		}
		return message;
	}
	
	public String getDeclinedLeaveRecallBackMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Requested leave recall back has been declined....";
		}
		else {
			message = "Enable to decline requested leave recall back....";
		}
		return message;
	}
	
	public String getApprovedLeaveRecallBackMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave recall back has been approved....";
		}
		else {
			message = "Enable to approve leave recall back....";
		}
		return message;
	}
	
	public String isStartDateEqualGreaterThanTodayErrorMessage() {
		return "Start Date is not equal or greater than today date.....";
	}
	
	public String getEmployeeLeaveDaysErrorMessage() {
		return "You have exceeded the number of your leave days.....";
	}
	
	public String getRedZoneErrorMessage() {
		return "Leave request is in a red zone period....";
	}
	
	public String getLeaveAlreadyExistErrorMessage() {
		return "Leave request already exist between the start date and end date...";
	}
	
	public String getEmployeeLeaveSentToSupervisorForApprovalMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave request has been sent to supervisor for approval...";
		}
		else {
			message = "Enable send leave request to supervisor for approval....";
		}
		return message;
	}
	
	public String getEmployeeLeaveSaveAsDraftMessage(String result) {
		String message = null;
		if(result.toLowerCase().equals("success")) {
			message = "Leave request has been drafted...";
		}
		else {
			message = "Enable draft leave request....";
		}
		return message;
	}

}
