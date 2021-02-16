package com.cust.hrms.dao;



public class FileUploadDao {
	
	private final String URL = "/C:/Users/Banks_Olo2/OneDrive/Documents/GitHub/hrms/src/main/webapp/"; 
	
	public String getUrl() {
		return URL;
	}
	public String getEmployeeProfilePictureUrl() {
		return "filesUpload/employeeImages/";
	}
	
	public String getSupportTicketurl() {
		return "filesUpload/supportTickets/";
	}
	
	public String getRequisitionUrl() {
		return "filesUpload/requisitions/";
	}
	
	public String getLeaveUrl() {
		return "filesUpload/leaveFiles/";
	}
}
