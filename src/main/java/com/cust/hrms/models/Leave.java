package com.cust.hrms.models;

public class Leave {
	private int leaveId;
	private int employeeId;
	private int departmentId;
	private int supervisorId;
	private int leaveTypeId;
	private String startDate;
	private String endDate;
	private String resumptionDate;
	private int noOfDays;
	private int primaryReliefOfficeId;
	private int secondaryReliefOfficeId;
	private String staffToNotify;
	private String comment;
	private String inlineWithLeavePlan;
	private int leaveStatusId;
	private int createdBy;
	private String createdAt;
	private int updatedBy;
	private String updatedAt;
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}
	public int getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getResumptionDate() {
		return resumptionDate;
	}
	public void setResumptionDate(String resumptionDate) {
		this.resumptionDate = resumptionDate;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getPrimaryReliefOfficeId() {
		return primaryReliefOfficeId;
	}
	public void setPrimaryReliefOfficeId(int primaryReliefOfficeId) {
		this.primaryReliefOfficeId = primaryReliefOfficeId;
	}
	public int getSecondaryReliefOfficeId() {
		return secondaryReliefOfficeId;
	}
	public void setSecondaryReliefOfficeId(int secondaryReliefOfficeId) {
		this.secondaryReliefOfficeId = secondaryReliefOfficeId;
	}
	public String getStaffToNotify() {
		return staffToNotify;
	}
	public void setStaffToNotify(String staffToNotify) {
		this.staffToNotify = staffToNotify;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getInlineWithLeavePlan() {
		return inlineWithLeavePlan;
	}
	public void setInlineWithLeavePlan(String inlineWithLeavePlan) {
		this.inlineWithLeavePlan = inlineWithLeavePlan;
	}
	public int getLeaveStatusId() {
		return leaveStatusId;
	}
	public void setLeaveStatusId(int leaveStatusId) {
		this.leaveStatusId = leaveStatusId;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "Leave [leaveId=" + leaveId + ", employeeId=" + employeeId + ", departmentId=" + departmentId
				+ ", supervisorId=" + supervisorId + ", leaveTypeId=" + leaveTypeId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", resumptionDate=" + resumptionDate + ", noOfDays=" + noOfDays
				+ ", primaryReliefOfficeId=" + primaryReliefOfficeId + ", secondaryReliefOfficeId="
				+ secondaryReliefOfficeId + ", staffToNotify=" + staffToNotify + ", comment=" + comment
				+ ", inlineWithLeavePlan=" + inlineWithLeavePlan + ", leaveStatusId=" + leaveStatusId + ", createdBy="
				+ createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt
				+ "]";
	}
	
	
}
