package com.cust.hrms.models;

public class ElementStatus {
	private int elementStatusId;
	private String name;
	private String code;
	private String createdAt;
	private String updatedAt;
	public int getElementStatusId() {
		return elementStatusId;
	}
	public void setElementStatusId(int elementStatusId) {
		this.elementStatusId = elementStatusId;
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
		return "ElementStatus [elementStatusId=" + elementStatusId + ", name=" + name + ", code=" + code
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}