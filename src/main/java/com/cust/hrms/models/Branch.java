package com.cust.hrms.models;

public class Branch {
	private int branchId;
	private String name;
	private String createdAt;
	private String updatedAt;
	private int createdBy;
	private int updatedBy;
	
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
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return "Branch [branchId=" + branchId + ", name=" + name + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}
	
	
}
