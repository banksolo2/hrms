package com.cust.hrms.dao;



public class FileUploadDao {
	
	private final String URL = "/home/bankstech/eclipse-workspace/hrms/src/main/webapp/filesUpload/"; 
	
	public String getEmployeeProfilePictureUrl() {
		return URL+"employeeImages/";
	}
	
	public String getSupportTicketurl() {
		return URL+"supportTickets/";
	}
}
