package com.cust.hrms.models;

public class LeaveType {
	private int leaveTypeId;
	private String name;
	private String code;
	private int days;
	private int createdBy;
	private String createdAt;
	private int updatedBy;
	private String updatedAt;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
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
		return "LeaveType [leaveTypeId=" + leaveTypeId + ", name=" + name + ", code=" + code + ", days=" + days
				+ ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
}
