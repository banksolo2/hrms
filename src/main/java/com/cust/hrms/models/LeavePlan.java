package com.cust.hrms.models;

public class LeavePlan {
	private int leavePlanId;
	private int employeeId;
	private int departmentId;
	private int departmentHeadId;
	private String startDate;
	private String endDate;
	private int noOfDays;
	private int leavePlanStatusId;
	private String onBehalf;
	private String comment;
	private String createdAt;
	private int createdBy;
	private String updatedAt;
	private int updatedBy;
	
	public int getLeavePlanId() {
		return leavePlanId;
	}
	public void setLeavePlanId(int leavePlanId) {
		this.leavePlanId = leavePlanId;
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
	public int getDepartmentHeadId() {
		return departmentHeadId;
	}
	public void setDepartmentHeadId(int departmentHeadId) {
		this.departmentHeadId = departmentHeadId;
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
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getLeavePlanStatusId() {
		return leavePlanStatusId;
	}
	public void setLeavePlanStatusId(int leavePlanStatusId) {
		this.leavePlanStatusId = leavePlanStatusId;
	}
	public String getOnBehalf() {
		return onBehalf;
	}
	public void setOnBehalf(String onBehalf) {
		this.onBehalf = onBehalf;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		return "LeavePlan [leavePlanId=" + leavePlanId + ", employeeId=" + employeeId + ", departmentId=" + departmentId
				+ ", departmentHeadId=" + departmentHeadId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", noOfDays=" + noOfDays + ", leavePlanStatusId=" + leavePlanStatusId + ", onBehalf=" + onBehalf
				+ ", comment=" + comment + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	
	
}
