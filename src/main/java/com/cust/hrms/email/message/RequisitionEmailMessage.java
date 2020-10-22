package com.cust.hrms.email.message;

import com.cust.hrms.email.*;

public class RequisitionEmailMessage {
	private String message;
	private String subject;
	private HrmsEmail he = new HrmsEmail();
	
	public void getRequisitionForSupervisorAuthorizationMessage(String emailAddress[], String data[]) {
		subject = "Requisition Awaits Supervisor Authorization";
		message = "<p>Dear "+data[0]+",</p>";
		message += "<p>";
		message += "A requisition awaits your authorization click on the link provided below to view:";
		message += "</p>";
		message +="<p><a href=\""+data[1]+"\">Click Here.</a></p>";
		message +="<p>Regards,<br/>Hr.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public static void main(String args[]) {
		RequisitionEmailMessage rem = new RequisitionEmailMessage();
		String emailAddress[] = {"seunolo2@gmail.com"};
		String data[] = {"SEJ", "#"};
		rem.getRequisitionForSupervisorAuthorizationMessage(emailAddress, data);
	}
}
