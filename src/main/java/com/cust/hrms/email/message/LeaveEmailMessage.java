package com.cust.hrms.email.message;

import com.cust.hrms.email.*;

public class LeaveEmailMessage {
	private String message;
	private String subject;
	private String url;
	private HrmsEmail he = new HrmsEmail();
	
	public void getEmployeeSendLeaveToSupervisorForApprovalMessage(String emailAddress[], String data[]) {
		subject = "Leave Request Sent To Supervisor For Approval";
		if(data[1].toLowerCase().equals("with_pay")) {
			url = he.getSiteUrl()+"employeePendingLeaveRequestWithPay.jsp";
		}
		else {
			url = he.getSiteUrl()+"employeePendingLeaveRequestWithoutPay.jsp";
		}
		message = "<p>Dear "+data[0]+",</p>";
		message += "<p>";
		message += "Your leave request has been sent to your supervisor for approval, "
				+ "<a href=\""+url+"\">click here</a> to view.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getSupervisorLeaveAwaitingApproval(String emailAddress[], String data[]) {
		/*data value order {
		 *1) Name Initials, 
		 *2) days,
		 *3) leave type, 
		 *4) start date, 
		 *5) end date,
		 *6) resumption date,
		 *7) name of the employee
		 *8) staff ID 
		 *9) leave option  [with_pay or without_pay]
		 * }*/
		subject = "Leave Request Awaiting Supervisor Approval";
		String leaveOption = "without pay";
		url = he.getSiteUrl()+"supervisorLeavePendingApprovalWithoutPay.jsp";
		if(data[8].toLowerCase().equals("with_pay")) {
			url = he.getSiteUrl()+"supervisorLeavePendingApprovalWithPay.jsp";
			leaveOption = "with pay";
		}
		String str = "";
		int days = Integer.parseInt(data[1]);
		if(days > 1 ) {
			str ="s";
		}
		message = "<p>Dear "+data[0]+",</p>";
		message +="<p>";
		message +="You have "+data[1]+" day"+str+" "+data[2].toLowerCase()+" leave "+leaveOption+" awaiting your approval, "
				+ "which start "+data[3]+" to "+data[4]+", and expected to resume on "+data[5]+". "
				+ "This was sent by "+data[6]+" with staff number "+data[7]+". "
				+ "Please <a href=\""+url+"\">click here</a> to view and approve.";
		message +="</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public static void main(String args[]) {
		LeaveEmailMessage lem = new LeaveEmailMessage();
		String emailAddress[] = {"Femex2006wes@gmail.com"};
		String data[] = {
				"ABF",
				"5",
				"Annual",
				"27 October 2020",
				"3 November 2020",
				"Oluwaseun Joseph Olotu",
				"2034"
		};
		lem.getSupervisorLeaveAwaitingApproval(emailAddress, data);
	}
}
