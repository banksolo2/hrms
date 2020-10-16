package com.cust.hrms.notification;

public class SupportTicketNotification {
	private String message;
	
	public String getClosedSupportTicketMessage(boolean result) { 
		if(result) {
			message = "Support ticket has been closed.....";
		}
		else {
			message = "Enable to close support ticket.....";
		}
		return message;
	}
	
	public String getPendingSupportTicketMessage(boolean result) {
		if(result) {
			message = "Support ticket has been initialize....";
		}
		else {
			message = "Enable to initialize support ticket....";
		}
		return message;
	}
	
	public String getResolvedSupportTicketMessage(boolean result) {
		if(result) {
			message = "Support ticket has been resolved......";
		}
		else {
			message = "Enable to resolve support ticket.....";
		}
		return message;
	}
	
	public String getUnresolvedSupportTicketMessage(boolean result) {
		if(result) {
			message = "Sent back unresolved support ticket.....";
		}
		else {
			message = "Enable to send back support ticket that was not resolved....";
		}
		return message;
	}
	
	public String getSupportTicketAlreadyExistMessage() {
		return "Support Ticket already exist...";
	}
	
	public static void main(String args[]) {
		SupportTicketNotification stn = new SupportTicketNotification();
		String message = stn.getClosedSupportTicketMessage(false);
		System.out.println(message);
	}

}
