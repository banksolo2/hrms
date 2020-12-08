package com.cust.hrms.models;

public class PayElement {
	private int payElementId;
	private String name;
	private String code;
	private int elementStatusId;
	private String description;
	private String startDate;
	private String endDate;
	private int createdBy;
	private int updatedBy;
	private String createdAt;
	private String updatedAt;
	
	
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
	public int getPayElementId() {
		return payElementId;
	}
	public void setPayElementId(int payElementId) {
		this.payElementId = payElementId;
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
	public int getElementStatusId() {
		return elementStatusId;
	}
	public void setElementStatusId(int elementStatusId) {
		this.elementStatusId = elementStatusId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
		return "PayElement [payElementId=" + payElementId + ", name=" + name + ", code=" + code + ", elementStatusId="
				+ elementStatusId + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
}
