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
	
	public void getHrLeaveRequestAwaitingApproval(String emailAddress[], String data[]) {
		/*Data values order {
		 * 1) No of days =====================================[0]
		 * 2) Leave Type =====================================[1]
		 * 3) Employee Full Name =============================[2]
		 * 4) Employee Staff ID ==============================[3]
		 * 5) Start Date =====================================[4]
		 * 6) End Date =======================================[5]
		 * 7) Resumption Date ================================[6]
		 * 8) Leave Type Option [with_pay or without_pay] ====[7]
		 * }*/
		subject = "Leave Request Awaits Approval";
		String str = (Integer.parseInt(data[0]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[7].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"hrLeavePendingApprovalWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"hrLeavePendingApprovalWithoutPay.jsp";
		}
		
		message = "<p>Dear HR,</p>";
		message +="<p>";
		message +=data[0]+" day"+str+" "+data[1].toLowerCase()+" leave request "+leaveOption+" awaits your approval, "
				+ "which was requested by "+data[2]+" with staff ID "+data[3]+". Which is expected starts "
				+ "from "+data[4]+" to "+data[5]+" and also expected to resume on "+data[6]+". "
				+ "Please <a href=\""+url+"\">click here</a> to view the leave request.";
		message +="</p>";
		message +="<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getEmployeeApprovedLeaveRequestMessage(String emailAddress[], String data[]) {
		/*data values order{
		 * 1) employee Initials [0]
		 * 2) number of days [1]
		 * 3) Leave Type [2]
		 * 4) Start date [3]
		 * 5) End date	[4]
		 * 6) Resumption Date [5]
		 * 7) Leave Option [ with_Pay or without_pay ] [6]
		 * }*/
		subject = "Leave Request Has Been Approved";
		String str = "";
		if(Integer.parseInt(data[1]) > 1) str="s";
		String leaveOption = null;
		if(data[6].equals("with_pay")) {
			url = he.getSiteUrl()+"employeeApprovedLeaveRequestWithPay.jsp";
			leaveOption = "with pay";
		}
		else {
			url = he.getSiteUrl()+"employeeApprovedLeaveRequestWithoutPay.jsp";
			leaveOption = "without pay";
		}
		message = "<p>Dear "+data[0]+",</p>";
		message += "<p>";
		message += "Your "+data[1]+" day"+str+" "+data[2].toLowerCase()+" leave "+leaveOption+" has been approved, Which starts from "
				+ data[3]+" to "+data[4]+". And you excepted to resume on "+data[5]+". "
				+ "Please <a href=\""+url+"\">click here</a> to view leave details.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getHrApprovedLeaveRequestMessage(String emailAddress[], String data[]) {
		/* Data value order{
		 * 1) No of leave days ===============================[0]
		 * 2) Leave Type =====================================[1]
		 * 3) Employee Full Name =============================[2]
		 * 4) Employee Staff ID ==============================[3]
		 * 5) Start Date =====================================[4]
		 * 6) End Date =======================================[5]
		 * 7) Resumption Date ================================[6]
		 * 8) Supervisor Name ================================[7]
		 * 9) SuperVisor Staff ID ============================[8]
		 * 10) Leave Option [with_pay or without_pay] ========[9]
		 * }*/
		subject="Approved Leave Request";
		String str =(Integer.parseInt(data[0]) > 1) ? "s" : "";
		String typeOption = null;
		if(data[9].toLowerCase().equals("with_pay")) {
			url = he.getSiteUrl()+"hrLeaveApprovedWithPay.jsp";
			typeOption = "with pay";
		}
		else {
			url = he.getSiteUrl()+"hrLeaveApprovedWithoutPay.jsp";
			typeOption = "without pay";
		}
		String statement = null;
		if(data[7].trim().toLowerCase().equals("hr")) {
			statement = "HR";
		}
		else {
			statement = data[7]+" with staff ID "+data[8];
		}
		message = "<p>Dear HR,</p>";
		message += "<p>";
		message += data[0]+" day"+str+" of "+data[1].toLowerCase()+" leave "+typeOption+" which was requested by "+data[2]+" with "
				+ "the staff ID "+data[3]+" has been approved, which starts from "+data[4]+" to "+data[5]+". "
				+ "And expected to resume on "+data[6]+", this leave request was approved by "+statement
				+", <a href=\""+url+"\">click here</a> to view the approved leave request.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getEmployeeLeaveSentHrForApprovalMessage(String emailAddress[], String data[]) {
		/* Data values order {
		 * 1) Employee Name initials ==============================[0]
		 * 2) Leave Option [with_pay or without_pay] ==============[1]
		 * }*/
		subject = "Leave Request Sent To HR For Approval";
		String leaveOption = null;
		if(data[1].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"employeePendingLeaveRequestWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"employeePendingLeaveRequestWithoutPay.jsp";
		}
		
		message ="<p>Dear "+data[0].toUpperCase()+",</p>";
		message +="<p>";
		message +="Your leave request "+leaveOption+" has been sent to HR for approval. "
				+ "Please <a href=\""+url+"\">click here</a> to view the leave request.";
		message +="</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getReliefOfficerLeaveRequestApprovedMessage(String emailAddress[], String data[]) {
		/* Data values order {
		 * 1) Employee full name ======================================[0]
		 * 2) Staff ID ================================================[1]
		 * 3) No of days ==============================================[2]
		 * 4) Leave Type ==============================================[3]
		 * 5) Start Date ==============================================[4]
		 * 6) End Date ================================================[5]
		 * 7) Resumption Date =========================================[6]
		 * 8) Employee Name Initials ==================================[7]
		 * 9) Leave Option [with_pay or Without_pay] ==================[8]
		 * } */
		subject = "Approved Leave Information";
		String str = (Integer.parseInt(data[2]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[8].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
		}
		else {
			leaveOption = "without pay";
		}
		
		message = "<p>Dear Employee,</p>";
		message += "<p>";
		message += "Be informed that "+data[0]+" with staff ID "+data[1]+" will be going on a "+data[2]+" day"+str+" "+data[3].toLowerCase()+" leave "+leaveOption+", "
				+ "which starts from "+data[4]+" to "+data[5]+". And expected to resume on "+data[6]+", "
				+ "so you are expected to step in for the employee when "+data[7].toUpperCase()+"'s leave starts.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getStaffNotifyLeaveRequestApprovedMessage(String emailAddress[], String data[]) {
		/* Data values order {
		 * 1) Employee Full Name =============================[0]
		 * 2) Staff ID =======================================[1]
		 * 3) No Of Days =====================================[2]
		 * 4) Leave Type =====================================[3]
		 * 5) Start Date =====================================[4]
		 * 6) End Date =======================================[5]
		 * 7) Resumption Date ================================[6]
		 * 8) Leave Option [with_pay or without_pay] =========[7]
		 * }*/
		subject = "Approved Leave Request Notification";
		String str = (Integer.parseInt(data[2]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[7].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
		}
		else {
			leaveOption = "without pay";
		}
		
		message = "<p>Dear Employee,</p>";
		message += "<p>";
		message += "Be informed that "+data[0]+" with staff ID "+data[1]+" will be going on "
				+ "a "+data[2]+" day"+str+" "+data[3].toLowerCase()+" leave "+leaveOption+", "
				+ "which starts from "+data[4]+" to "+data[5]+". "
				+ "And expected to resume on "+data[6]+".";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getEmployeeDeclinedLeaveRequestMessage(String emailAddress[], String data[]) {
		/*Data values order {
		 * 1) Employee Name Initials =======================================[0]
		 * 2) No of leave days =============================================[1]
		 * 3) Leave type ===================================================[2]
		 * 4) Start Date ===================================================[3]
		 * 5) End Date =====================================================[4]
		 * 6) Declined By Name =============================================[5]
		 * 7) Declined By Staff ID =========================================[6]
		 * 8) Comment ======================================================[7]
		 * 9) Leave Option [with_pay or without_pay] =======================[8]
		 * }*/
		subject = "Your Leave Request Has been Declined";
		String str = (Integer.parseInt(data[1]) > 1) ? "s" : "" ;
		String leaveOption = null;
		if(data[8].toLowerCase().equals("with_pay")) {
			url = he.getSiteUrl()+"employeeDeclinedLeaveRequestWithPay.jsp";
			leaveOption = "with pay";
		}
		else {
			url = he.getSiteUrl()+"employeeDeclinedLeaveRequestWithoutPay.jsp";
			leaveOption = "without pay";
		} 
		String statement = null;
		if(data[5].trim().toLowerCase().equals("hr")) {
			statement = "HR";
		}
		else {
			statement = data[5]+" with staff ID "+data[6];
		}
		message = "<p>Dear "+data[0].toUpperCase()+",</p>";
		message += "<p>";
		message += "Your "+data[1]+" day"+str+" "+data[2].toUpperCase()+" leave "+leaveOption+", that was meant to "
				+ "start on "+data[3]+" and end on "+data[4]+". Has been declined by "+statement
				+", for this reason <b>“"+data[7]+"”</b>. "
				+ "Please <a href=\""+url+"\">click here</a> to view the declined leave request.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getHrLeaveRequestDeclinedMessage(String emailAddress[], String data[]) {
		/*Data values order{
		 * 1) Number of leave days ===================================================[0]
		 * 2) Leave Type =============================================================[1]
		 * 3) Requester Full Name ====================================================[2]
		 * 4) Requester staff ID =====================================================[3]
		 * 5) Start Date =============================================================[4]
		 * 6) End Date ===============================================================[5]
		 * 7) Declined By Full Name ==================================================[6]
		 * 8) Declined By Staff ID ===================================================[7]
		 * 9) Comment ================================================================[8]
		 * 10) Leave Option [with_pay or without_pay] ================================[9]
		 * }*/
		subject = "Declined Employee Leave Request";
		String str = (Integer.parseInt(data[0]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[9].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"hrLeaveDeclinedWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"hrLeaveDeclinedWithoutPay.jsp";
		}
		String statement = null;
		if(data[6].trim().toLowerCase().equals("hr")) {
			statement = "HR";
		}
		else {
			statement = data[6]+" with staff ID "+data[7];
		}
		message = "<p>Dear HR,</p>";
		message += "<p>";
		message += data[0]+" day"+str+" "+data[1].toLowerCase()+" leave "+leaveOption+", which was requested "
				+ "by "+data[2]+" with staff ID "+data[3]+". Which was expected to start on "+data[4]+" and "
				+ "end on "+data[5]+". Has been declined by "+statement+" for this reason "
				+ "<b>“"+data[8]+"”</b>. Please <a href=\""+url+"\">click here</a> to view the declined leave request.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getSupervisorDeclinedLeaveRequest(String emailAddress[], String data[]) {
		/*Data values order{
		 * 1) Supervisor initials ====================================================[0]
		 * 2) Number of leave days ===================================================[1]
		 * 3) Leave Type =============================================================[2]
		 * 4) Requester Full Name ====================================================[3]
		 * 5) Requester staff ID =====================================================[4]
		 * 6) Start Date =============================================================[5]
		 * 7) End Date ===============================================================[6]
		 * 8) Comment ================================================================[7]
		 * 9) Leave Option [with_pay or without_pay] =================================[8]
		 * }*/
		subject = "Declined Leave Request";
		String str = (Integer.parseInt(data[1]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[8].trim().toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"supervisorDeclinedLeaveRequestReportWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"supervisorDeclinedLeaveRequestReportWithoutPay.jsp";
		}
		message = "<p>Dear "+data[0]+",</p>";
		message += "<p>";
		message += data[1]+" day"+str+" "+data[2].toLowerCase()+" leave "+leaveOption+", which was requested "
				+ "by "+data[3]+" with staff ID "+data[4]+". Which was expected to start on "+data[5]+" and "
				+ "end on "+data[6]+". Has been declined by HR for this reason "
				+ "<b>“"+data[7]+"”</b>. Please <a href=\""+url+"\">click here</a> to view the declined leave request.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getEmployeeLeaveRequestSentBackForCorrection(String emailAddress[], String data[]) {
		/*Data values order {
		 * 1) Employee Name Initials =========================================================[0]
		 * 2) Number Of Leave Days ===========================================================[1]
		 * 3) Leave Type =====================================================================[2]
		 * 4) Start Date =====================================================================[3]
		 * 5) End Date =======================================================================[4]
		 * 6) Correction By Name =============================================================[5]
		 * 7) Correction Staff ID ============================================================[6]
		 * 8) Comment ========================================================================[7]
		 * 9) Leave Option [with_pay or without_pay] =========================================[8]
		 * }*/
		subject = "Leave Request Sent Back For Correction";
		String str = (Integer.parseInt(data[1]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[8].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"employeeLeaveRequestSentBackForCorrectionWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"employeeLeaveRequestSentBackForCorrectionWithoutPay.jsp";
		}
		String statement = null;
		if(data[5].trim().toLowerCase().equals("hr")) {
			statement = "HR";
		}
		else {
			statement = data[5]+" with staff ID "+data[6];
		}
		message = "<p>Dear "+data[0].toUpperCase()+",</p>";
		message += "<p>";
		message += "Your "+data[1]+" day"+str+" "+data[2].toLowerCase().trim()+" leave request "+leaveOption+", which is expected to start "
				+ "on "+data[3]+" and end on "+data[4]+". Has been sent back for correction "
				+ "by "+statement+", for this reason, <b>“"+data[7]+"”</b>. "
				+ "Please <a href=\""+url+"\">click here</a> to view the leave request.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getHrInitializedLeaveRecallBackMessage(String emailAddress[], String data[]) {
		/* Data values order {
		 *1) Number of Leave Days ========================================================[0]
		 *2) Leave Type ==================================================================[1]
		 *3) Start Date ==================================================================[2]
		 *4) End Date ====================================================================[3]
		 *5) Employee Full Name ==========================================================[4]
		 *6) Employee Staff ID ===========================================================[5]
		 *7) Initialized By Full Name ====================================================[6]
		 *8) Initialized By Staff ID =====================================================[7]
		 *9) Comment =====================================================================[8]
		 *10)Leave Options [with_pay or without_pay] =====================================[9]
		 * }*/ 
		subject = "Leave Recall Back Initialized";
		String str = (Integer.parseInt(data[0]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[9].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"hrLeaveRecallBackPendingApproval.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"hrLeaveRecallBackPendingApprovalWithoutPay.jsp";
		}
		
		message = "<p>Dear HR,</p>";
		message += "<p>";
		message += "Leave recall back has been initialized for a "+data[0]+" day"+str+" "+data[1].toLowerCase()+" leave "+leaveOption+", "
				+ "which started on "+data[2]+" and expected to end on "+data[3]+". Which was approved "
				+ "for "+data[4]+" with staff ID "+data[5]+", and leave recall back was initiated "
				+ "by "+data[6]+" with staff ID "+data[7]+" for this reason <b>“"+data[8]+"”</b>. "
				+ "Please <a href=\""+url+"\">click here</a> to view the leave.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getSupervisorDelinedLeaveRecallBackRequestMessage(String emailAddress[], String data[]) {
		/*Data values order {
		 * 1) Supervisor Name Initials =======================================================[0]
		 * 2) Number of Leave Days ===========================================================[1]
		 * 3) Leave Type =====================================================================[2]
		 * 4) Employee Full Name =============================================================[3]
		 * 5) Staff ID =======================================================================[4]
		 * 6) Start Date =====================================================================[5]
		 * 7) End Date =======================================================================[6]
		 * 8) Comment ========================================================================[7]
		 * 9) Leave Option [With_pay or without_pay] =========================================[8]
		 * }*/
		subject = "Leave Recall back Declined";
		String str = (Integer.parseInt(data[1]) > 1) ? "s" : "";
		String leaveOption = null;
		if(data[7].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"supervisorApprovedLeaveRequestReportWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"supervisorApprovedLeaveRequestReportWithoutPay.jsp";
		}
		
		message = "<p>Dear "+data[0].toUpperCase()+", </p>";
		message +="<p>";
		message +="Your leave recall back request for "+data[1]+" day"+str+" "+data[2].toLowerCase()+" leave "+leaveOption+", "
				+ "which was approved for "+data[3]+" with staff ID "+data[4]+", which started on "+data[5]+" and ends on "+data[6]+". "
				+ "Has been declined by HR for this reason <b>“"+data[7]+"”</b>. "
				+ "Please <a href=\""+url+"\">click here</a> to view the declined leave recall back.";
		message +="</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getSupervisorApprovedLeaveRecalledBackMessage(String emailAddress[], String data[]) {
		/*Data values order {
		 * 1) Supervisor Name Initials =============================================================[0]
		 * 2) Employee Full Name ===================================================================[1]
		 * 3) Staff ID =============================================================================[2]
		 * 4) Leave Option [with_pay or without_pay] ===============================================[3]
		 * }*/
		
		if(data[3].toLowerCase().equals("with_pay")) {
			url = he.getSiteUrl()+"supervisorLeaveRecallBackApprovedWithPay.jsp";
		}
		else {
			url = he.getSiteUrl()+"supervisorLeaveRecallBackApprovedWithoutPay.jsp";
		}
		subject = "Approved Leave Recall back";
		message = "<p>Dear "+data[0].toUpperCase()+",</p>";
		message += "<p>";
		message += "Your request for leave recall back for "+data[1]+" with staff ID "+data[2]+", "
				+ "has been approved by HR. Please <a href=\""+url+"\">click here</a> to view the approved leave recalled back.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public void getEmployeeApprovedLeaveRecalledBackMessage(String emailAddress[], String data[]) {
		/* Data values order {
		 * 1) Employee Name Initials ==============================================================[0]
		 * 2) Supervisor Full Name ================================================================[1]
		 * 3) Supervisor Staff ID =================================================================[2]
		 * 4) Leave Type ==========================================================================[3]
		 * 5) Resumption Date =====================================================================[4]
		 * 6) Leave Options [with_pay or without_pay] =============================================[5]
		 * }*/
		subject = "Approved Leave Recalled Back";
		String leaveOption = null;
		if(data[5].toLowerCase().equals("with_pay")) {
			leaveOption = "with pay";
			url = he.getSiteUrl()+"employeeApprovedLeaveRecalledBackWithPay.jsp";
		}
		else {
			leaveOption = "without pay";
			url = he.getSiteUrl()+"employeeApprovedLeaveRecalledBackWithoutPay.jsp";
		}
		message = "<p>Dear "+data[0].toUpperCase()+",</p>";
		message += "<p>";
		message += "Be Informed that a leave recalled back has been approved by HR, which was initialized by "
				+ data[1]+" with staff ID "+data[2]+" on your "+data[3].toLowerCase()+" leave "+leaveOption+". And you are now "
				+ "expected to resume on "+data[4]+". Please note that your remaining leave days will be added back "
				+ "to your leave balance. Please <a href=\""+url+"\">click here</a> to view the approved leave recalled back.";
		message += "</p>";
		message += "<p>Regards,<br/>HR.</p>";
		he.sendEmail(emailAddress, message, subject);
	}
	
	public static void main(String args[]) {
		LeaveEmailMessage lem = new LeaveEmailMessage();
		String emailAddress[] = {
				"seunolo2@gmail.com", 
				"Femex2006wes@gmail.com"
		};
		String data[] = {
			"SBJ",
			"Oluwaseun Joseph Olotu",
			"2345",
			"Annual",
			"06 November 2020",
			"with_pay"
		};
		lem.getEmployeeApprovedLeaveRecalledBackMessage(emailAddress, data);
		
	}
}
