package com.cust.hrms.models;

public class SupportTicket {
	private int supportTicketId;
	private String issueReportDate;
	private int issueTypeId;
	private String issueDescription;
	private String fileUrl;
	private String issueFor;
	private int departmentId;
	private int supportTicketStatusId;
	private String employees;
	private String comment;
	private int resolvedBy;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	
	
	public int getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(int resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	public int getSupportTicketId() {
		return supportTicketId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getSupportTicketStatusId() {
		return supportTicketStatusId;
	}
	public void setSupportTicketStatusId(int supportTicketStatusId) {
		this.supportTicketStatusId = supportTicketStatusId;
	}
	public void setSupportTicketId(int supportTicketId) {
		this.supportTicketId = supportTicketId;
	}
	public String getIssueReportDate() {
		return issueReportDate;
	}
	public void setIssueReportDate(String issueReportDate) {
		this.issueReportDate = issueReportDate;
	}
	public int getIssueTypeId() {
		return issueTypeId;
	}
	public void setIssueTypeId(int issueTypeId) {
		this.issueTypeId = issueTypeId;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getIssueFor() {
		return issueFor;
	}
	public void setIssueFor(String issueFor) {
		this.issueFor = issueFor;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getEmployees() {
		return employees;
	}
	public void setEmployees(String employees) {
		this.employees = employees;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "SupportTicket [supportTicketId=" + supportTicketId + ", issueReportDate=" + issueReportDate
				+ ", issueTypeId=" + issueTypeId + ", issueDescription=" + issueDescription + ", fileUrl=" + fileUrl
				+ ", issueFor=" + issueFor + ", departmentId=" + departmentId + ", supportTicketStatusId="
				+ supportTicketStatusId + ", employees=" + employees + ", comment=" + comment + ", resolvedBy="
				+ resolvedBy + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	
}
