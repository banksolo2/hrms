package com.cust.hrms.models;

public class SupportTicketStatus {
	private int supportTicketStatusId;
	private String name;
	private String code;
	private String createdAt;
	private String updatedAt;
	public int getSupportTicketStatusId() {
		return supportTicketStatusId;
	}
	public void setSupportTicketStatusId(int supportTicketStatusId) {
		this.supportTicketStatusId = supportTicketStatusId;
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
		return "SupportTicketStatus [supportTicketStatusId=" + supportTicketStatusId + ", name=" + name + ", code="
				+ code + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
