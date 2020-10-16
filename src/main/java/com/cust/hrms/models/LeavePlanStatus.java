package com.cust.hrms.models;

public class LeavePlanStatus {
	private int leavePlanStatusId;
	private String name;
	private String code;
	private String createdAt;
	private String updatedAt;
	public int getLeavePlanStatusId() {
		return leavePlanStatusId;
	}
	public void setLeavePlanStatusId(int leavePlanStatusId) {
		this.leavePlanStatusId = leavePlanStatusId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
		return "LeavePlanStatus [leavePlanStatusId=" + leavePlanStatusId + ", name=" + name + ", code=" + code
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
