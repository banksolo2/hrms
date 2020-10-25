package com.cust.hrms.email.message;

import com.cust.hrms.email.*;

public class RequisitionEmailMessage {
	private String message;
	private String subject;
	private HrmsEmail he = new HrmsEmail();
	private String url = null;
	
	public void getRequisitionForSupervisorAuthorizationMessage(String emailAddress[], String data[]) {
		subject = "Requisition Awaits Supervisor Authorization";
		url = he.getSiteUrl()+"allPendingSupervisorRequisitionReport.jsp";
		message = "<p>Dear "+data[0]+",</p>";
		message += "<p>";
		message += "A requisition awaits your authorization click on the link provided below to view:";
		message += "</p>";
		message +="<p><a href=\""+url+"\">Click Here.</a></p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getRequisitionSentRecipientsForApprovalMessage(String emailAddress[], String data[]) {
		subject = "Requisition Awaits Recipients Approval";
		url = he.getSiteUrl()+"allPendingRecipientRequisitionReport.jsp";
		message = "<p>Dear Employee,</p>";
		message +="<p>";
		message +="A requisiton with Requisition Number: "+data[0]+" is awaiting your approval, "
				+ "<a href=\""+url+"\">click here</a> to view.";
		message +="</p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getRequisitionRequesterDeclinedMessage(String emailAddress[], String data[]) {
		subject = "Requisition Declined";
		url = he.getSiteUrl()+"allDeclinedEmployeeRequisitionReport.jsp";
		message = "<p>Dear "+data[0]+",</p>";
		message +="<p>";
		message +="Requisition with number: "+data[1]+" has been declined by "+data[2]+" for this reason <b>\""+data[3]+"\"</b>. "
				+ "Please <a href=\""+url+"\">click here</a> to view.";
		message +="</p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getRequisitionRequesterApprovedMessage(String emailAddress[], String data[]) {
		subject = "Requisition Approved";
		url = he.getSiteUrl()+"allApprovedEmployeeRequisitionReport.jsp";
		message = "<p>Dear "+data[0]+",</p>";
		message += "<p>";
		message +="Requisition with number: "+data[1]+" has been approved by "+data[2]+". "
				+ "Please <a href=\""+url+"\">click here</a> to view.";
		message += "</p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getRequisitionRecipientsApprovedMessage(String emailAddress[], String data[]) {
		subject ="Requisition Approved";
		message = "<p>Dear Employee,</p>";
		message +="<p>";
		message +="Requisition with number: "+data[0]+" has been approved by "+data[1]+".";
		message +="</p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getRequisitionRecipientsDeclinedMessage(String emailAddress[], String data[]) {
		subject ="Requisition Declined";
		message = "<p>Dear Employee,</p>";
		message +="<p>";
		message +="Requisition with number: "+data[0]+" has been Declined by "+data[1]+".";
		message +="</p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	
	
	
	public static void main(String args[]) {
		RequisitionEmailMessage rem = new RequisitionEmailMessage();
		String emailAddress[] = {"seunolo2@gmail.com"};
		String data[] = {"SEJ"};
		rem.getRequisitionForSupervisorAuthorizationMessage(emailAddress, data);
	}
}
