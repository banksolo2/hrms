package com.cust.hrms.email.message;

import com.cust.hrms.email.*;

public class SupportTicketEmailMessage {
	private String message;
	private HrmsEmail he = new HrmsEmail();
	
	public void getinitializeSupportTicketMessage(String emailAddress[],  String data[] ) { 
		String subject = "Pending Support Ticket";
		message ="<style>";
		message +="table, th, td { border: 0px solid black;}";
		message +="</style>";
		message +="<p>Dear Employee,</p>" ;
		message +="<p>A new support ticket has been initialized see details below:</p>";
		message +="<table>";
		message +="<tr>";
		message +="<th>Issue Report Date: </th>";
		message +="<td>"+data[0]+"</td>";
		message +="</tr>";
		message +="<tr>";
		message +="<th>Created By: </th>";
		message +="<td>"+data[1]+"</td>";
		message +="</tr>";
		message +="<tr>";
		message +="<th>Issue Type: </th>";
		message +="<td>"+data[2]+"</td>";
		message +="</tr>";
		message +="<tr>";
		message +="<th>Issue Description:</th>";
		message +="<td>"+data[3]+"</td>";
		message +="</tr>";
		message +="</table>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getResolveSupportTicketMessage(String emailAddress[], String data[]) {
		String subject = "Resolved Support Ticket";
		String url = null;
		if(data[0].equals("employees")) {
			url = he.getSiteUrl()+"employeeResolveSupportTicketReport.jsp";
		}
		else {
			url = he.getSiteUrl()+"departmentResolveSupportTicketReport.jsp";
		}
		message ="<p>Dear "+ data[1]+"</p>";
		message +="<p>";
		message +="Support ticket with ticket number "+data[2]+" has been resolved. Click link below to view:";
		message +="</p>";
		message +="<p><a href=\""+url+"\">Click Here</a></p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
		
	}
	
	public void getUnresolvedSupportTicketMessage(String emailAddress[], String data[]) {
		String subject = "Unresolved Support Ticket";
		String url = null;
		if(data[0].equals("employees")) {
			url = he.getSiteUrl()+"allPendingSupportTicketReportForEmployee.jsp";
		}
		else {
			url = he.getSiteUrl()+"allPendingSupportTicketReportForDepartment.jsp";
		}
		message = "<p>Dear employee,</p>";
		message +="<p>";
		message +="Support ticket with ticket number "+data[1]+" is still unresolved for the reason "
				+ "<b>\""+data[2]+"\"</b>. Click link below to view:";
		message +="</p>";
		message +="<p><a href=\""+url+"\">Click Here</a></p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	
	
	
	public static void main(String args[]) {
		SupportTicketEmailMessage stem = new SupportTicketEmailMessage();
		String emailAddress[] = {"seunolo2@gmail.com", "Femex2006wes@gmail.com"};
		String data[] = {"13 October, 2020", "Oluwaseun Joseph Olotu (4567)", "Red Zone Error", "Leave red zone issue"};
		stem.getinitializeSupportTicketMessage(emailAddress, data);
	}

}
