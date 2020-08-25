package com.cust.hrms.models;

public class MartialStatus {
	private int martialStatusId;
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
	public int getMartialStatusId() {
		return martialStatusId;
	}
	public void setMartialStatusId(int martialStatusId) {
		this.martialStatusId = martialStatusId;
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
		return "MartialStatus [martialStatusId=" + martialStatusId + ", name=" + name + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}
	
	
}
