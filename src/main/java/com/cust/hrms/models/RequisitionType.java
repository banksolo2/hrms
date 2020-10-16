package com.cust.hrms.models;

public class RequisitionType {
	private int requisitionTypeId;
	private String name;
	private String code;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	public int getRequisitionTypeId() {
		return requisitionTypeId;
	}
	public void setRequisitionTypeId(int requisitionTypeId) {
		this.requisitionTypeId = requisitionTypeId;
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
		return "RequisitionType [requisitionTypeId=" + requisitionTypeId + ", name=" + name + ", code=" + code
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
}
